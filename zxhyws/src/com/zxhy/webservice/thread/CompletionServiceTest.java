package com.zxhy.webservice.thread;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceTest {
	
	
	public static void testExecutorCompletionService() throws InterruptedException, ExecutionException {
		int numThread = 5;
		ExecutorService executior = Executors.newFixedThreadPool(numThread);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executior);
		
		for(int i = 0 ; i < numThread ; i++) {
			
			//submit 传一个接口的实现类
			//提交一次开一个线程
		       completionService.submit(new Task(i));	
		}
		
		
		for(int i = 0;i < numThread; i++) {
			System.out.println(completionService.take().get());
		}
		
	}
	
	public static void main(String[] args) {
		try {
			testExecutorCompletionService();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
