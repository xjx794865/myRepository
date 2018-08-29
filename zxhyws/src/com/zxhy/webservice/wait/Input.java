package com.zxhy.webservice.wait;

import java.util.HashMap;
import java.util.Map;


import com.zxhy.webservice.entity.Message;

public class Input implements Runnable {
//	private Resource r ;
//	
//	public Input(Resource r){
//		this.r = r;
//	}
	
	
	
	
	public void run() {
	
			
			String receive = ThreadDemo.map.get("001");
		if(receive == null) {
			ThreadDemo.map.put("001", "xjx");
			System.out.println(ThreadDemo.map);
		}else {
			System.out.println(ThreadDemo.map);
		}
		
	
		
		
		
	//	System.out.println(map);
		
		
	}

}