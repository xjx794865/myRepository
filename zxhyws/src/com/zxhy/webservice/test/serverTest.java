package com.zxhy.webservice.test;

import javax.xml.ws.Endpoint;

import com.zxhy.webservice.ws.UpgradeVersionImpl;


/*
 * 发布webservice
 */
public class serverTest {
	public static void main(String[] args) {
		String addr = "http://192.168.204.1:9000/ws/hellows";
		Endpoint.publish(addr, new UpgradeVersionImpl());
		System.out.println("发布成功");
	}
	

}
