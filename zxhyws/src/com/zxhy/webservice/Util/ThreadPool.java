package com.zxhy.webservice.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.sun.media.jfxmedia.logging.Logger;
import com.zxhy.webservice.entity.Message;

public class ThreadPool {
	/**
	 * ͨ��ExecutiorService����һ���̳߳� submit��һ���ӿ�ʵ���� ���ڴ���ÿ���߳������صķ��� �ύһ�ο�һ���߳� ����������ߺŵĶ���
	 * ��̬������߳�
	 * 
	 * @param antennas
	 * @param content
	 */
	
	
	public static void ExecutorCompletionService(String[] antennas, String content) {

		// String receiveMessage = null;

		int numThread = antennas.length + 1;
		// �̳߳����߳�װ����
		ExecutorService executior = Executors.newFixedThreadPool(numThread);

		

		// �ж϶˿��Ƿ�ռ��
		 if (PortUtil.isPortUsing(6000)) {
		 executior.execute(new MyUDPReceive());
		 }

		for (int i = 0; i < antennas.length; i++) {

			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			 executior.execute(new MyRunnable(antennas[i], content));
			// UDPreceive3.map.put(antennas[i],null);
			//System.out.println(new MyRunnable(antennas[i], content));
			//MyUDPReceive1.map.put(antennas[i], new MyRunnable(antennas[i], content));
			//System.out.println(MyUDPReceive1.map);
		}

		 //executior.shutdown();

	}

	public static void ExectorCompletionServiceForUpgrate(String[] antennas, String AMC, String VERSION, String SIZE,
			String size) {

		int numThread = antennas.length + 1;

		ExecutorService executior = Executors.newFixedThreadPool(numThread);

		// CompletionService<Object> completionService = new
		// ExecutorCompletionService<Object>(executior);

		//executior.submit(new MyUDPReceive());

		for (int i = 0; i < antennas.length; i++) {

			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			executior.submit(new Mycallable(antennas[i], AMC, VERSION, SIZE, size));
		}

	}

}
