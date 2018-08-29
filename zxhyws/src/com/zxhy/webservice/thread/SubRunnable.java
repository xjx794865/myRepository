package com.zxhy.webservice.thread;


import java.util.concurrent.Callable;

import com.zxhy.webservice.Util.UDPClient;

public class SubRunnable implements Callable<Object>{
	
	
	private String antenna;
	
	private String content;
	

	public SubRunnable(String antenna, String content) {
       
		this.antenna = antenna;
		this.content = content;
		
	}
	
	

	@Override
	public Object call() throws Exception {
		
	
		try
		{
			UDPClient.SendMessage(antenna, content);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}
	}


