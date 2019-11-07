package cn.how2j.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;


@SpringBootApplication
@EnableEurekaClient
//断路器
@EnableHystrix
//启动缓存
@EnableCaching
public class IndexGatherStoreApplication {
	
	public static void main(String[] args) {
		int port = 0;
		//启动类使用的端口是8001
		int defaultPort = 8001;
		int eurekaServerPort = 8761;
		int redisPort = 6379;
		port = defaultPort;
		
		//通过端口号判断eureka是否启动
		if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
			System.err.printf("检测到%d端口未启用，判断eureka服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort);
			System.exit(1);
		}
		
		//通过端口号判断redis是否启动
		if(NetUtil.isUsableLocalPort(redisPort)) {
			System.err.printf("检测到端口%d未启用，判断redis服务器没有启动，本服务无法使用，故退出%n", redisPort);
			System.exit(1);
		}
		
		if(null != args && 0 != args.length) {
			for(String arg: args) {
				String strPort = StrUtil.subAfter(arg, "port=", true);
				if(NumberUtil.isNumber(strPort)) {
					port = Convert.toInt(strPort);
				}
			}
		}
		
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		
		new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);
	}
	
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
