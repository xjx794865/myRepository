package com.zxhy.webservice.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.sun.media.jfxmedia.logging.Logger;
import com.zxhy.webservice.entity.Message;
import com.zxhy.webservice.entity.UpdateInfo;

public class ThreadPool {
	/**
	 * ͨ��ExecutiorService����һ���̳߳� submit��һ���ӿ�ʵ���� ���ڴ���ÿ���߳������صķ��� �ύһ�ο�һ���߳� ����������ߺŵĶ���
	 * ��̬������߳�
	 * 
	 * @param antennas
	 * @param content
	 */
	
	
	public static void ExecutorCompletionService(UpdateInfo info) {

		List<String> terms = info.terms;
		String content = info.content;
		
		// �̳߳����߳�װ����
		ExecutorService executior = Executors.newFixedThreadPool(terms.size()+1);
		
		// �ж϶˿��Ƿ�ռ��
		if (PortUtil.isPortUsing(6000)) 
		{
			System.out.println("Start UDP revieve thread...");
			executior.execute(new MyUDPReceive1());
		}

		for (int i = 0; i < terms.size(); i++) 
		{
			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			MyRunnable2 updateThread = new MyRunnable2(terms.get(i), content);
			executior.execute(updateThread);
			MyUDPReceive1.map.put(terms.get(i), updateThread);
		}
		 //executior.shutdown();
	}

	public static void ExectorCompletionServiceForUpgrate(UpdateInfo info) 
	{
		
//String[] antennas, String AMC, String VERSION, String SIZE, String size --- from content
/*		
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
*/
	}
}
