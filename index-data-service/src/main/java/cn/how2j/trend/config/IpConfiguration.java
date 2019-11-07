package cn.how2j.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {
	
	private int serverPort;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		// TODO Auto-generated method stub
		this.serverPort = event.getWebServer().getPort();
	}
	
	public int getPort() {
		return this.serverPort;
	}

}
