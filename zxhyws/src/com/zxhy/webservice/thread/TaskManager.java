package com.zxhy.webservice.thread;

import java.util.Timer;

public class TaskManager {
	private static final long PERIOD =10 * 1000;
	
	public   static void taskManager() {
		Timer timer = new Timer();
		FixedTimerTask task = new FixedTimerTask();
		System.out.println("start");
		
		
		//0��ʾ����ִ��һ�Σ��Ժ�ÿ��һ��ʱ��ִ��һ��
		timer.schedule(task, 0, PERIOD);
		
		//1000��ʾ1���ִ��һ�Σ��Ժ�ÿ��һ��ʱ��ִ��һ��
		//timer.schedule(task, 1000, PERIOD);
		
		//0��ʾ����ִ��һ�Σ��Ժ�ÿ��һ��ʱ��ִ��һ��
		//timer.schedule(task, 1000, PERIOD);
		
		// �ڵ���14��4������ִ��һ�Σ��Ժ���ִ��
		//timer.schedule(task, bookTime(15,0,0));
		
		//�ڵ���14��4������ִ��һ�Σ��Ժ�ÿ��һ��ʱ��ִ��һ��
		//���ʱ�䳬�����趨ʱ�䣬������ִ��һ��
//		timer.schedule(task, bookTime(0,34,10),PERIOD);
//		timer.scheduleAtFixedRate(task, bookTime(0,40,0),PERIOD);
 

		
	}

}
