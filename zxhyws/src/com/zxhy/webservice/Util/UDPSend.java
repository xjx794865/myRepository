package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend {
	/*
	 * 实现UDP协议的发送端: 实现封装数据的类 java.net.DatagramPacket 将你的数据包装 实现数据传输的类
	 * java.net.DatagramSocket 将数据包发出去
	 * 
	 * 实现步骤: 1. 创建DatagramPacket对象,封装数据, 接收的地址和端口 2. 创建DatagramSocket 3.
	 * 调用DatagramSocket类方法send,发送数据包 4. 关闭资源
	 * 
	 * DatagramPacket构造方法: DatagramPacket(byte[] buf, int length, InetAddress
	 * address, int port)
	 * 
	 * DatagramSocket构造方法: DatagramSocket()空参数 方法: send(DatagramPacket d)
	 * 
	 */

	public static void main(String[] args) throws IOException {
		/*
		 * //创建数据包对象,封装要发送的数据,接收端IP,端口 byte[] date = "你好UDP".getBytes();
		 * //创建InetAddress对象,封装自己的IP地址 InetAddress inet =
		 * InetAddress.getByName("127.0.0.1"); DatagramPacket dp = new
		 * DatagramPacket(date, date.length, inet,6000);
		 * //创建DatagramSocket对象,数据包的发送和接收对象 DatagramSocket ds = new DatagramSocket();
		 * //调用ds对象的方法send,发送数据包 ds.send(dp); //关闭资源 ds.close();
		 */
		// 创建一个客户端DatagramSocket，使用随机端口
		while(true) {
			
			 DatagramSocket ds = null;
			// String content = "[GUID]\nA_guid=2F6EBF60-60EE-D88E-4E33-19D343483842\n[NET]\nB_vlanid = 176\nC_hostname = www.zxhyzs.cn\nD_dns = 1\nE_ip = 118.178.126.145\n[SATLITE]\nF_th_power = -30\nG_th_agc = 40500\nH_th_timelock = 6500\nI_th_CN = 40\nJ_tuner_model = 0903\n[TRACER]\nK_delay_step = 16\nL_lock_steady_count = 0\nM_max_searh_speed = 30\nN_delay_step = 16\nO_step_delay_count = 4\nP_speed_division = 10.0\nQ_lock_speed_division = 16.0\n[MONITOR]\nR_th_error = 20\n[MONITOR]\nS_interval = 20\n";
			//  System.out.println(content);	
			 //String message = StringUtil.toMessage(content);
				
				try {
					// 创建数据包传输对象DatagramSocket 绑定端口号

					ds = new DatagramSocket(8900);
					// 创建字节数组
					byte[] data = new byte[1024];
					// 创建数据包对象,传递字节数组
					DatagramPacket dp = new DatagramPacket(data, data.length);

					// 调用ds对象的方法receive传递数据包
					ds.receive(dp);

					// 获取发送端的ip地址对象
					String ip = dp.getAddress().getHostAddress();

					// 获取发送的端口号
					int port = dp.getPort();

					byte[] date2 = "U,X,2DAA1000,2.2,1024,2048".getBytes();
				
					DatagramPacket response = new DatagramPacket(date2, date2.length, dp.getSocketAddress());
				
					
					try {
						Thread.sleep(6000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ds.send(response);

					 System.out.println(new String(data , 0 , data.length) +"...."+ip+":"+port);
					//System.out.println(ip + ":" + port);

				} finally {
					if (ds != null) {
						ds.close();

					}
				}

			}
	}

}
