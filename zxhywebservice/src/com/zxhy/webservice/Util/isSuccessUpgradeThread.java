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
		
		//���ն˺�
		List<String> terms = new ArrayList<>();
		//���ߵ��ն˺�
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
		
		//��������
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
		System.out.println("��Ŀǰ���߲��Ҳ�������ʧ�ܵ��ն˽����ٴ�����");
			ExecutorService executiorP = Executors.newFixedThreadPool(OnlineTerms.size());
			for (int j = 0; j < OnlineTerms.size(); j++)
			{
				// submit ��һ���ӿڵ�ʵ����
				// �ύһ�ο�һ���߳�
				MyRunnable updateThread = new MyRunnable(OnlineTerms.get(j), termsAndContent.get(OnlineTerms.get(j)));
				executiorP.execute(updateThread);
				MyUDPReceive.map.put(OnlineTerms.get(j)+"P", updateThread);
				
			} 	
			
			terms.clear();
			OnlineTerms.clear();
			termsAndContent.clear();
			executiorP.shutdown();
			
			
	    //��������
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
		System.out.println("��Ŀǰ���߲��ҳ�������ʧ�ܵ��ն˽����ٴ�����");
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
			
		//���ǲ�������
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
		System.out.println("��Ŀǰ���߲������ǲ�������ʧ�ܵ��ն˽����ٴ�����");
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
