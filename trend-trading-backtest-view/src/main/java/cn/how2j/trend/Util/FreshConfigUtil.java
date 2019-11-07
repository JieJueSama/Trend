package cn.how2j.trend.Util;

import java.util.HashMap;

import cn.hutool.http.HttpUtil;

public class FreshConfigUtil {
	
	public static void main(String[] args) {

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json;charset=utf-8");
		System.out.println("因为要去git获取，还要刷新index-config-server，会比较卡，所一般需要好几秒才能完成，请耐心等待");
		
		String result = HttpUtil.createPost("http://localhost:8041/actuator/bus-refresh").addHeaders(headers).execute().body();
		System.out.println("result:" + result);
		System.out.println("refresh完成");
		
	}

}
