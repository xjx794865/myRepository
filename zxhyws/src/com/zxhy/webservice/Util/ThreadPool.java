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
	 * 通过ExecutiorService创建一个线程池 submit传一个接口实现类 用于创建每个线程所加载的方法 提交一次开一个线程 这里根据天线号的多少
	 * 动态的添加线程
	 * 
	 * @param antennas
	 * @param content
	 */
	
	
	public static void ExecutorCompletionService(String[] antennas, String content) {

		// String receiveMessage = null;

		int numThread = antennas.length + 1;
		// 线程池中线程装多少
		ExecutorService executior = Executors.newFixedThreadPool(numThread);

		

		// 判断端口是否被占用
		 if (PortUtil.isPortUsing(6000)) {
		 executior.execute(new MyUDPReceive());
		 }

		for (int i = 0; i < antennas.length; i++) {

			// submit 传一个接口的实现类
			// 提交一次开一个线程
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

			// submit 传一个接口的实现类
			// 提交一次开一个线程
			executior.submit(new Mycallable(antennas[i], AMC, VERSION, SIZE, size));
		}

	}

}
