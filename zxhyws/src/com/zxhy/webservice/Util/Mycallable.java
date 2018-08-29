package com.zxhy.webservice.Util;

import java.util.concurrent.Callable;

public class Mycallable implements Runnable {
	
	private String antenna;
	
	private String AMC;
	
	private String VERSION;
	
	private String SIZE;
	
	private String size;
	
	
	

	public Mycallable(String antenna, String aMC, String vERSION, String sIZE, String size2) {
		
		this.antenna = antenna;
		this.AMC = aMC;
		this.VERSION = vERSION;
		this.SIZE = sIZE;
		this.size = size2;
	}




	@Override
	public void run()  {
		try {
		UDPClient.sendMessageForUpgrateProgram(antenna, AMC, VERSION, size, SIZE);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
