package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
public class UDPReceive2 {

	public static void Receive() throws IOException {
		
			DatagramSocket ds = null;
			// DatagramSocket socket = null;

			try {
				// �������ݰ��������DatagramSocket �󶨶˿ں�

				ds = new DatagramSocket(5000);
				// �����ֽ�����
				byte[] data = new byte[1024];
				// �������ݰ�����,�����ֽ�����
				DatagramPacket dp = new DatagramPacket(data, data.length);

				// ����ds����ķ���receive�������ݰ�
				ds.receive(dp);
				String u = new String(data, 0, dp.getLength());
				System.out.println(u + "��xjx");
				// ��ȡ���Ͷ˵�ip��ַ����

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