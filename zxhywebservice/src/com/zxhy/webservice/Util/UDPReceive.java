
package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



/*
 *  ʵ��UDP���ն�
 *    ʵ�ַ�װ���ݰ� java.net.DatagramPacket �����ݽ���
 *    ʵ���������     java.net.DatagramSocket �������ݰ�
 *    
 *  ʵ�ֲ���:
 *     1. ����DatagramSocket����,�󶨶˿ں�
 *         Ҫ�ͷ��Ͷ˶˿ں�һ��
 *     2. �����ֽ�����,���շ���������
 *     3. �������ݰ�����DatagramPacket
 *     4. ����DatagramSocket���󷽷�
 *        receive(DatagramPacket dp)��������,���ݷ������ݰ���
 *     5. ���
 *          ���͵�IP��ַ
 *            ���ݰ�����DatagramPacket����getAddress()��ȡ���Ƿ��Ͷ˵�IP��ַ����
 *            ����ֵ��InetAddress����
 *          ���յ����ֽڸ���
 *            ���ݰ�����DatagramPacket���� getLength()
 *          ���ͷ��Ķ˿ں�
 *            ���ݰ�����DatagramPacket���� getPort()���Ͷ˿�
 *     6. �ر���Դ
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
				// �������ݰ��������DatagramSocket �󶨶˿ں�

				ds = new DatagramSocket(8900);
				// �����ֽ�����
				byte[] data = new byte[1024];
				// �������ݰ�����,�����ֽ�����
				DatagramPacket dp = new DatagramPacket(data, data.length);

				// ����ds����ķ���receive�������ݰ�
				ds.receive(dp);

				// ��ȡ���Ͷ˵�ip��ַ����
				String ip = dp.getAddress().getHostAddress();

				// ��ȡ���͵Ķ˿ں�
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
		

