package com.zxhy.ws.server;

import javax.xml.ws.Endpoint;

import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class ServerPulish {
	
	public static void main(String[] args) {
		
		String address =  "http://118.178.126.145:12345/zxhywebservice/upgradeversion";
		
		Endpoint.publish(address, new UpgradeVersionImpl());
		
		System.out.println("发布成功");
		
	}

}
