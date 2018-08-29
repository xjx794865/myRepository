package com.zxhy.webservice.wait;

import java.util.HashMap;
import java.util.Map;

import com.zxhy.webservice.Util.StringUtil;
import com.zxhy.webservice.entity.Area;

public class ThreadDemo {
	public static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {

		Runnable runn = new Input();

		Thread thread = new Thread(runn);

		thread.start();

		map.put("001", null);
		map.put("002", "jack");

	}
}
