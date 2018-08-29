package com.zxhy.webservice.thread;

import java.io.IOException;

import com.zxhy.webservice.Util.MyUDPReceive;
import com.zxhy.webservice.Util.MyUDPReceive1;
import com.zxhy.webservice.Util.PortUtil;
import com.zxhy.webservice.ws.UpgradeVersion;
import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class TestDemo {
	public static void main(String[] args) throws IOException {
		
		
		//String antenna_no = "001";
		String content= "[GUID]\nA_guid=2F6EBF60-60EE-D88E-4E33-19D343483843\n[NET]\nB_vlanid = 176\nC_hostname = www.zxhyzs.cn\nD_dns = 1\nE_ip = 118.178.126.145\n[SATLITE]\nF_th_power = -30\nG_th_agc = 40500\nH_th_timelock = 6500\nI_th_CN = 40\nJ_tuner_model = 0903\n[TRACER]\nK_delay_step = 16\nL_lock_steady_count = 0\nM_max_searh_speed = 30\nN_delay_step = 16\nO_step_delay_count = 4\nP_speed_division = 10.0\nQ_lock_speed_division = 16.0\n[MONITOR]\nR_interval = 20";
		//String content="";
		String[] antennas = {"001","002"};
		//String content = "[GUID]\nA_guid=2F6EBF60-60EE-D88E-4E33-19D343483842\n";
		UpgradeVersion UV = new UpgradeVersionImpl();
		
		//UV.insertAndSendByNo(antenna_no, content);
		
		UV.insertAllAndSend(antennas, content);
		//UV.sendAllForUpgrateProgram(antennas, "3789", "2.2", "1024", "2048");
		
		//TaskManager.taskManager();
		
		
	
	}

}
