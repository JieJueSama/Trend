package cn.how2j.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import cn.hutool.core.util.NetUtil;

//表示这是一个启动类
@SpringBootApplication
//表示这是一个注册中心服务器
@EnableEurekaServer
public class EurekaServerApplication {
	
	public static void main(String[] args) {
		//8761这个端口是默认的  后面的子项目都会访问这个端口
		int port = 8761;
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" + port).run(args);
	}

}
