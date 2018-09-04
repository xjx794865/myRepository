package com.zxhy.webservice.thread;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.zxhy.webservice.Util.PidUtils;

public class FixedTimerTask extends TimerTask{

	@Override
	public void run() {
		
		String[] antennas = null;
		try {
			antennas = PidUtils.selectAntennaByPid();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		DateFormat ddtf = DateFormat.getDateTimeInstance();
		Date d = new Date();
		for (int i = 0 ; i < 3;i++) {
			try {
				Thread.sleep(3000);
				System.out.println("已执行【"+(i+1)+"】秒钟，at"+ddtf.format(d));
			}catch(InterruptedException e ) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("本次任务调度结束，at:"+ ddtf.format(new Date()));
		System.out.println("-------------------------------------");
		
	}

}
