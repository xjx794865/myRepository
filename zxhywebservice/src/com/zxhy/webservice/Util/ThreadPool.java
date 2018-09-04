package com.zxhy.webservice.Util;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		ExecutorService executior = Executors.newFixedThreadPool(terms.size());
        
		for (int i = 0; i < terms.size(); i++)
		{
			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			MyRunnable updateThread = new MyRunnable(terms.get(i), content);
			executior.execute(updateThread);
			MyUDPReceive.map.put(terms.get(i), updateThread);
		}
		// executior.shutdown();
	}

	public static void ExectorCompletionServiceForUpgrate(UpdateInfo info) {

		// String[] antennas, String AMC, String VERSION, String SIZE, String size ---
		// from content

		List<String> terms = info.terms;
		String content = info.content;

		int numThread = terms.size();

		ExecutorService executior = Executors.newFixedThreadPool(numThread);
	
		for (int i = 0; i < terms.size(); i++) 
		{

			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			MyProgramRunnable updateProgram = new MyProgramRunnable(terms.get(i), content);
			executior.execute(updateProgram);
			MyUDPReceive.map.put(terms.get(i), updateProgram);
		}

	}

	public static void ExectorCompletionServiceForSatellite(UpdateInfo info) 
	{

		List<String> terms = info.terms;
		String content = info.content;

		int numThread = terms.size();
		ExecutorService executior = Executors.newFixedThreadPool(numThread);

		for (int i = 0; i < terms.size(); i++)
		{

			// submit ��һ���ӿڵ�ʵ����
			// �ύһ�ο�һ���߳�
			MySatelliteRunnable updatesatellite = new MySatelliteRunnable(terms.get(i), content);
			executior.execute(updatesatellite);
			MyUDPReceive.map.put(terms.get(i), updatesatellite);
		}

	}
}
