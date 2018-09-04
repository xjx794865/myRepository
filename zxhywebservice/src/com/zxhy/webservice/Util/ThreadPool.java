package com.zxhy.webservice.Util;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zxhy.webservice.entity.UpdateInfo;

public class ThreadPool {
	/**
	 * 通过ExecutiorService创建一个线程池 submit传一个接口实现类 用于创建每个线程所加载的方法 提交一次开一个线程 这里根据天线号的多少
	 * 动态的添加线程
	 * 
	 * @param antennas
	 * @param content
	 */

	public static void ExecutorCompletionService(UpdateInfo info) {

		List<String> terms = info.terms;
		String content = info.content;

		// 线程池中线程装多少
		ExecutorService executior = Executors.newFixedThreadPool(terms.size());
        
		for (int i = 0; i < terms.size(); i++)
		{
			// submit 传一个接口的实现类
			// 提交一次开一个线程
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

			// submit 传一个接口的实现类
			// 提交一次开一个线程
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

			// submit 传一个接口的实现类
			// 提交一次开一个线程
			MySatelliteRunnable updatesatellite = new MySatelliteRunnable(terms.get(i), content);
			executior.execute(updatesatellite);
			MyUDPReceive.map.put(terms.get(i), updatesatellite);
		}

	}
}
