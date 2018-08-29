package com.zxhy.webservice.test;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.zxhy.webservice.Util.ThreadPool;
import com.zxhy.webservice.thread.TaskManager;
import com.zxhy.webservice.thread.ThreadDemo;
import com.zxhy.webservice.ws.UpgradeVersion;
import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class DemoTest {
	
	
	
	@Test
	@Ignore
	public void fun2() {
		String content= "[GUID]\nGUID=1fea55fb-ffcc-4e2b-95ba-11be91109998\n";
		String antenna_no = "001";
		UpgradeVersion UV = new UpgradeVersionImpl();
		try {
			UV.insertAndSendByNo(antenna_no, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void fun3() {
		TaskManager.taskManager();
	}

}
