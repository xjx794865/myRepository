package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * MyRunnableʵ����Callable�ӿ� ��Ҫ��дcall���� call��������ÿ���߳���Ҫ���еķ���
 * 
 * @author Lenovo
 *
 */
public class MyRunnable implements Runnable {

	public String Msg = null;
	
	private String flag = "P";

	private String antenna;

	private String content;
	
	private Semaphore recvSemophore = new Semaphore(0);

	/**
	 * ������ ���ڴ������ߺź�content ���ڴ�������ߺŲ�ͬ ���͵��ն˾Ͳ�ͬ
	 * 
	 * @param antenna
	 * @param content
	 */
	public MyRunnable(String antenna, String content) {

		this.antenna = antenna;
		this.content = content;

	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(MyRunnable.class);
		Properties props = new Properties();
		try {
			props.load(MyRunnable.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

		String receivedMessage = null;
		

		// U,P,content
		String message = StringUtil.toMessage(content);

		// ͨ�����ӳػ�����ݿ�����
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		while (true) 
		{			
			System.out.println("start sendMessageUtil...");
			SendMessageUtil.sendContent(antenna, message,flag);
			
			boolean isSemAvilable = false;
			try {
				isSemAvilable = recvSemophore.tryAcquire(30000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(isSemAvilable)
			{
				receivedMessage = Msg;
	
					String errHead = antenna + ":";
					if (receivedMessage.equals("U,P,SUCCESS")) 
					{
						try {
							String sql = "UPDATE tbl_upgraderecord SET successUpgrade = 1 WHERE antenna_no = ?";
							PreparedStatement pstat = conn.prepareStatement(sql);
							pstat.setString(1, antenna);
							pstat.executeUpdate();
	
							conn.commit();
							pstat.close();
							conn.close();
							logger.debug(antenna + ":���������ɹ�");
						} catch (Exception e) {
							e.printStackTrace();
						}
						errHead = "";
					} 
					else if(receivedMessage.equals("U,P,ERROR,FORMAT"))
					{
							errHead +="������ʽ����(";
					}
					else if(receivedMessage.equals("U,P,WARN,SAME-GUID")) 
					{
						errHead += "�����汾�ظ�(";
					}
					else if(receivedMessage.equals("U,P,ERROR,FAILED")) 
					{
						errHead += "��������(";
					}  
					else
					{
						errHead += "δ֪��Ӧ(";
					}
					if(!errHead.equals(""))
					{
						logger.error(errHead + receivedMessage + ")");
					}
					//thread terminated when error or success
					break;
				}	
			int online = SendMessageUtil.selectOnlineByMid(antenna);
			if(online == 0)
			{
				logger.error(antenna+"���ն˲����ߣ���ֹͣ����");
				//thread terminated when error
				break;
			}
		}
		
	}
	
	
	public void sendMsg(String Msg) {
		//�������߳̽��յ������ݴ���Msg
		this.Msg = Msg;
		//�ͷ�һ���ź���
		recvSemophore.release();
	}
}
