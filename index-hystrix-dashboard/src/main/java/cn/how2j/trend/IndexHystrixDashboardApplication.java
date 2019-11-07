package cn.how2j.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import cn.hutool.core.util.NetUtil;


@SpringBootApplication
//用来启动断路器监控台
@EnableHystrixDashboard
public class IndexHystrixDashboardApplication {
	
	public static void main(String[] args) {
		int port = 8070;
		int eurekaPort = 8761;
		
		
		if(NetUtil.isUsableLocalPort(eurekaPort)) {
			System.err.printf("检查到端口%d未启用，判断eureka服务器没有启动，本服务没法使用，故退出%n", eurekaPort);
			System.exit(1);
		}
		
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		
		new SpringApplicationBuilder(IndexHystrixDashboardApplication.class).properties("server.port=" + port).run(args);
	}

}
