package cn.how2j.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;



@SpringBootApplication
//表示注册为微服务
@EnableEurekaClient
public class ThirdPartIndexDataApplication {
	
	public static void main(String[] args) {
		int port = 8090;
		int eurekaServerPort = 8761;
		
		if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
			System.err.printf("检测到端口%d未启动，判断eureka服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort);
			System.exit(1);
		}
		
		//如果启动时带了参数
		if(null != args && args.length != 0) {
			for(String arg : args) {
				//如 port=8099 那么程序就会使用8099作为端口号
				//这样做好处   代码不需要更改，只需要在启动的时候带不同的参数，就可以启动不同的端口了
				if(arg.startsWith("port=")) {
					String strPort = StrUtil.subAfter(arg, "port=", true);
					if(NumberUtil.isNumber(strPort)) {
						port = Convert.toInt(strPort);
					}
				}
			}
		}
		
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port=" + port).run(args);
	}

}
