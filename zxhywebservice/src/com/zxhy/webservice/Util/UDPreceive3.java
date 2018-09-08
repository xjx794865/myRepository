package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zxhy.webservice.ws.UpgradeVersion;
import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class UDPreceive3 {

	public static void Receive() throws IOException {
			DatagramSocket ds = null;

			try {
				// 创建数据包传输对象DatagramSocket 绑定端口号

				ds = new DatagramSocket(7000);
				// 创建字节数组
				byte[] data = new byte[1024];
				// 创建数据包对象,传递字节数组
				DatagramPacket dp = new DatagramPacket(data, data.length);

				// 调用ds对象的方法receive传递数据包
				ds.receive(dp);
			//	System.out.println(new String(data, 0, dp.getLength()));
				// 获取发送端的ip地址对象

				InetAddress inet = InetAddress.getByName("127.0.0.1");

				byte[] date2 = "221363,U,P,OK".getBytes();

				DatagramPacket response = new DatagramPacket(date2, date2.length, inet, 6000);

				ds.send(response);
			

				// System.out.println(new String(data , 0 , data.length) +"...."+ip+":"+port);
				// System.out.println(ip + ":" + port);

			} finally {
				if (ds != null) {
					ds.close();

				}

			}
	

	}

	public static void main(String[] args) {
		try {
			UDPreceive3.Receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
