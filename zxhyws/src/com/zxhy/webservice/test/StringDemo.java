package com.zxhy.webservice.test;

import com.zxhy.webservice.Util.StringUtil;

public class StringDemo {
	
	public static void main(String[] args) {
		String content= "[GUID]\nGUID=1fea55fb-ffcc-4e2b-95ba-11be91109fbb\n";
		String Message = StringUtil.toMessage(content);
		System.out.println(Message);
	
	}
	
	
    
}
