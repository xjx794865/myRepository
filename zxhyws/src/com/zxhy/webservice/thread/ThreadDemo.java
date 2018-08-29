package com.zxhy.webservice.thread;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadDemo {
	
	public static void testExecutorCompletionService(String[] antennas, String content) {
		
		int numThread = antennas.length;
		ExecutorService executior = Executors.newFixedThreadPool(numThread);
		CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executior);

		for (int i = 0; i < antennas.length; i++) {

			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			completionService.submit(new SubRunnable(antennas[i], content));
		}

	}

}
