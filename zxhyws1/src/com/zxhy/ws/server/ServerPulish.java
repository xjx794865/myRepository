package com.zxhy.ws.server;

import javax.xml.ws.Endpoint;

import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class ServerPulish {
	
	public static void main(String[] args) {
		
		String address =  "http://192.168.204.1:3000/zxhywebservice/upgradeversion";
		
		Endpoint.publish(address, new UpgradeVersionImpl());
		
		System.out.println("发布成功");
		
	}

}
