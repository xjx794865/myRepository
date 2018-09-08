package com.zxhy.webservice.thread;

import java.util.Timer;

public class TaskManager {
	private static final long PERIOD =10 * 1000;
	
	public   static void taskManager() {
		Timer timer = new Timer();
		FixedTimerTask task = new FixedTimerTask();
		System.out.println("start");
		
		
		//0表示立即执行一次，以后每隔一段时间执行一次
		timer.schedule(task, 0, PERIOD);
		
		//1000表示1秒后执行一次，以后每隔一段时间执行一次
		//timer.schedule(task, 1000, PERIOD);
		
		//0表示立即执行一次，以后每隔一段时间执行一次
		//timer.schedule(task, 1000, PERIOD);
		
		// 在当天14点4分整，执行一次，以后不再执行
		//timer.schedule(task, bookTime(15,0,0));
		
		//在当天14点4分整，执行一次，以后每隔一段时间执行一次
		//如果时间超过了设定时间，会立即执行一次
//		timer.schedule(task, bookTime(0,34,10),PERIOD);
//		timer.scheduleAtFixedRate(task, bookTime(0,40,0),PERIOD);
 

		
	}

}
