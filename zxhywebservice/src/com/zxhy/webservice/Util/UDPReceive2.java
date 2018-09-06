package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 *  实现UDP接收端
 *    实现封装数据包 java.net.DatagramPacket 将数据接收
 *    实现输出传输     java.net.DatagramSocket 接收数据包
 *    
 *  实现步骤:
 *     1. 创建DatagramSocket对象,绑定端口号
 *         要和发送端端口号一致
 *     2. 创建字节数组,接收发来的数据
 *     3. 创建数据包对象DatagramPacket
 *     4. 调用DatagramSocket对象方法
 *        receive(DatagramPacket dp)接收数据,数据放在数据包中
 *     5. 拆包
 *          发送的IP地址
 *            数据包对象DatagramPacket方法getAddress()获取的是发送端的IP地址对象
 *            返回值是InetAddress对象
 *          接收到的字节个数
 *            数据包对象DatagramPacket方法 getLength()
 *          发送方的端口号
 *            数据包对象DatagramPacket方法 getPort()发送端口
 *     6. 关闭资源
 */
public class UDPReceive2 {

	public static void Receive() throws IOException {
		
			DatagramSocket ds = null;
			// DatagramSocket socket = null;

			try {
				// 创建数据包传输对象DatagramSocket 绑定端口号

				ds = new DatagramSocket(5000);
				// 创建字节数组
				byte[] data = new byte[1024];
				// 创建数据包对象,传递字节数组
				DatagramPacket dp = new DatagramPacket(data, data.length);

				// 调用ds对象的方法receive传递数据包
				ds.receive(dp);
				String u = new String(data, 0, dp.getLength());
				System.out.println(u + "：xjx");
				// 获取发送端的ip地址对象

				InetAddress inet = InetAddress.getByName("127.0.0.1");

				byte[] date2 = "001,U,P,OK".getBytes();
				// socket = new DatagramSocket();
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

	public static void main(String[] args) throws IOException {

		UDPReceive2.Receive();
	}

}