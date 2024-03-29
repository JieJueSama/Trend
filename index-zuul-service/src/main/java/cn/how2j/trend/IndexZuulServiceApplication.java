package cn.how2j.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
import cn.hutool.core.util.NetUtil;



@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulServiceApplication {
	
	//http://localhost:127.0.0.1:8301/api-codes/codes
	public static void main(String[] args) {
		int port = 8031;
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用了，无法启动%n",port);
			System.exit(1);
		}
		new SpringApplicationBuilder(IndexZuulServiceApplication.class).properties("server.port=" + port).run(args);
	}
	
	
	//表示一直在取样
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;	
	}

}
