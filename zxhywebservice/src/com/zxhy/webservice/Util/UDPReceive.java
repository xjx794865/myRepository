
package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



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
public class UDPReceive {



	public static void main(String[] args) throws IOException {
		while(true) {
		
		 DatagramSocket ds = null;
		 String content = "[GUID]\nA_guid=2F6EBF60-60EE-D88E-4E33-19D343483842\n[NET]\nB_vlanid = 176\nC_hostname = www.zxhyzs.cn\nD_dns = 1\nE_ip = 118.178.126.145\n[SATLITE]\nF_th_power = -30\nG_th_agc = 40500\nH_th_timelock = 6500\nI_th_CN = 40\nJ_tuner_model = 0903\n[TRACER]\nK_delay_step = 16\nL_lock_steady_count = 0\nM_max_searh_speed = 30\nN_delay_step = 16\nO_step_delay_count = 4\nP_speed_division = 100\nQ_lock_speed_division = 160\n[MONITOR]\nR_th_error = 200\n[MONITOR]\nS_interval = 20\n";
		String text ="[I]\n1\n[1]\nA_sat_lng=8750\nB_hunt_freq=1398500\nC_hunt_sbr=10833329\nD_lcl_osc=9750\n[2]\nA_sat_lng=7860\nB_hunt_freq=1134500\nC_hunt_sbr=20811129\nD_lcl_osc=7680\n[3]\nA_sat_lng=0\nB_hunt_freq=0\nC_hunt_sbr=0\nD_lcl_osc=0\n";
		
		//  System.out.println(content);	
		 String message = StringUtil.toMessage(content);
	     String message1 = StringUtil.toText(text);
	    // System.out.println(message1);
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

				byte[] date2 = message.getBytes();
				byte[] date3 = "U,X,2DAA1000,2,1,59988,166428".getBytes();
			    byte[] date4 = message1.getBytes();
			    
				DatagramPacket response = new DatagramPacket(date2, date2.length, dp.getSocketAddress());
				DatagramPacket response2 = new DatagramPacket(date3, date3.length, dp.getSocketAddress());
				DatagramPacket response3 = new DatagramPacket(date4, date4.length, dp.getSocketAddress());
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ds.send(response);
				ds.send(response2);
                ds.send(response3);
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
		

