package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend {
	/*
	 * ʵ��UDPЭ��ķ��Ͷ�: ʵ�ַ�װ���ݵ��� java.net.DatagramPacket ��������ݰ�װ ʵ�����ݴ������
	 * java.net.DatagramSocket �����ݰ�����ȥ
	 * 
	 * ʵ�ֲ���: 1. ����DatagramPacket����,��װ����, ���յĵ�ַ�Ͷ˿� 2. ����DatagramSocket 3.
	 * ����DatagramSocket�෽��send,�������ݰ� 4. �ر���Դ
	 * 
	 * DatagramPacket���췽��: DatagramPacket(byte[] buf, int length, InetAddress
	 * address, int port)
	 * 
	 * DatagramSocket���췽��: DatagramSocket()�ղ��� ����: send(DatagramPacket d)
	 * 
	 */

	public static void main(String[] args) throws IOException {
		/*
		 * //�������ݰ�����,��װҪ���͵�����,���ն�IP,�˿� byte[] date = "���UDP".getBytes();
		 * //����InetAddress����,��װ�Լ���IP��ַ InetAddress inet =
		 * InetAddress.getByName("127.0.0.1"); DatagramPacket dp = new
		 * DatagramPacket(date, date.length, inet,6000);
		 * //����DatagramSocket����,���ݰ��ķ��ͺͽ��ն��� DatagramSocket ds = new DatagramSocket();
		 * //����ds����ķ���send,�������ݰ� ds.send(dp); //�ر���Դ ds.close();
		 */
		// ����һ���ͻ���DatagramSocket��ʹ������˿�
		while(true) {
			
			 DatagramSocket ds = null;
			// String content = "[GUID]\nA_guid=2F6EBF60-60EE-D88E-4E33-19D343483842\n[NET]\nB_vlanid = 176\nC_hostname = www.zxhyzs.cn\nD_dns = 1\nE_ip = 118.178.126.145\n[SATLITE]\nF_th_power = -30\nG_th_agc = 40500\nH_th_timelock = 6500\nI_th_CN = 40\nJ_tuner_model = 0903\n[TRACER]\nK_delay_step = 16\nL_lock_steady_count = 0\nM_max_searh_speed = 30\nN_delay_step = 16\nO_step_delay_count = 4\nP_speed_division = 10.0\nQ_lock_speed_division = 16.0\n[MONITOR]\nR_th_error = 20\n[MONITOR]\nS_interval = 20\n";
			//  System.out.println(content);	
			 //String message = StringUtil.toMessage(content);
				
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
