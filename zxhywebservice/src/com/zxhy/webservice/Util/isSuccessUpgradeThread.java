package com.zxhy.webservice.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class isSuccessUpgradeThread implements Runnable {
	
	public static Semaphore recvSemophore = new Semaphore(0);

	@Override
	public void run()
	{
		
		//存终端号
		List<String> terms = new ArrayList<>();
		//在线得终端号
		List<String> OnlineTerms = new ArrayList<>();
		//<antenna,content>
		Map<String, String> termsAndContent = new HashMap<String, String>();
		
		while(true)
		{
			boolean isSemAvilable = false;
			try
			{
				isSemAvilable = recvSemophore.tryAcquire(30000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(isSemAvilable)
		{
		
		//参数升级
		termsAndContent = isSuccessUpgrade.selectAntennapBySuccessUpgrade();

		for (String key : termsAndContent.keySet()) 
		{
			terms.add(key);
		}
		for (int i = 0; i < terms.size(); i++) 
		{
			int online = SendMessageUtil.selectOnlineByMid(terms.get(i));
			if (online == 1) 
			{
				OnlineTerms.add(terms.get(i));
			}
		}
		System.out.println("对目前在线并且参数升级失败的终端进行再次推送");
			ExecutorService executiorP = Executors.newFixedThreadPool(OnlineTerms.size());
			for (int j = 0; j < OnlineTerms.size(); j++)
			{
				// submit 传一个接口的实现类
				// 提交一次开一个线程
				MyRunnable updateThread = new MyRunnable(OnlineTerms.get(j), termsAndContent.get(OnlineTerms.get(j)));
				executiorP.execute(updateThread);
				MyUDPReceive.map.put(OnlineTerms.get(j)+"P", updateThread);
				
			} 	
			
			terms.clear();
			OnlineTerms.clear();
			termsAndContent.clear();
			executiorP.shutdown();
			
			
	    //程序升级
		termsAndContent = isSuccessUpgrade.selectAntennaxBySuccessUpgrade();
		for(String key:termsAndContent.keySet())
		{
			terms.add(key);
		}
		for(int i = 0;i<terms.size();i++)
		{
			int online = SendMessageUtil.selectOnlineByMid(terms.get(i));
			if(online == 1)
			{
				OnlineTerms.add(terms.get(i));
			}
		}
		System.out.println("对目前在线并且程序升级失败的终端进行再次推送");
		ExecutorService executiorX = Executors.newFixedThreadPool(OnlineTerms.size());
		for(int j = 0; j <OnlineTerms.size();j++) 
		{
			MyProgramRunnable updateProgram = new MyProgramRunnable(OnlineTerms.get(j), termsAndContent.get(OnlineTerms.get(j)));
			executiorX.execute(updateProgram);
			MyUDPReceive.map.put(OnlineTerms.get(j)+"X", updateProgram);
		}
		
		terms.clear();
		OnlineTerms.clear();
		termsAndContent.clear();
		executiorX.shutdown();
			
		//卫星参数升级
		termsAndContent = isSuccessUpgrade.selectAntennasBySuccessUpgrade();
		for(String key:termsAndContent.keySet())
		{
			terms.add(key);
		}
		for(int i = 0;i < terms.size(); i++)
		{
			int online = SendMessageUtil.selectOnlineByMid(terms.get(i));
			if(online == 1)
			{
				OnlineTerms.add(terms.get(i));
			}		
		}
		System.out.println("对目前在线并且卫星参数升级失败的终端进行再次推送");
		ExecutorService executiorS = Executors.newFixedThreadPool(OnlineTerms.size());
		for(int j = 0;j<OnlineTerms.size();j++)
		{
			MySatelliteRunnable updatesatellite = new MySatelliteRunnable(OnlineTerms.get(j), OnlineTerms.get(j));
			executiorS.execute(updatesatellite);
			MyUDPReceive.map.put(OnlineTerms.get(j)+"S", updatesatellite);
		}
		
		terms.clear();
		OnlineTerms.clear();
		termsAndContent.clear();
		executiorS.shutdown();
		
	    	}//if(isSemAvilable)
	 
		}//whlie
		
	}	

}
