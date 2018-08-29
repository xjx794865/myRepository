package com.zxhy.webservice.test;

import java.util.Arrays;

public class Constructor {
	private String[] antennas;
	private String content;
	
	
	public Constructor(String[] antennas,String content) {
		
		this.antennas = antennas;
		this.content =  content;
		
		
	}
	
	
	


	public void fun() {
		for(int i = 0; i<antennas.length; i++) {
		System.out.println("ÌìÏßºÅ£º"+antennas[i]+"£¬ÄÚÈÝ£º"+content);
		}
	}	

}
