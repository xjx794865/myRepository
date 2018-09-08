package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyUDPReceive implements Runnable{

	public static Map<String, Object> map = new HashMap<String, Object>();

	public void run() {

		int num = 1;
		while (true) 
		{

			Logger logger = Logger.getLogger(MyUDPReceive.class);
			Properties props = new Properties();
			try
			{
				props.load(UDPreceive3.class.getClassLoader().getResourceAsStream("log4j.properties"));
			} catch (IOException e) 
			{
				logger.error(e.getMessage());
			}
			PropertyConfigurator.configure(props);

			// 设置接受端口
			DatagramSocket socket = null;
			try 
			{
				socket = new DatagramSocket(8900);
			} catch (SocketException e)
			{
				logger.error(e.getMessage());
				
			}

			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			try 
			{
				socket.receive(receivePacket);
			} catch (IOException e)
			{
				logger.error(e.getMessage()+"接收线程出错...");
	
			}

			// 得到返回信息的字符长度
			int length = receivePacket.getLength();
			// 得到String类型的返回信息
			String receiveMessage = new String(receiveData, 0, length);
			// 返回信息的开头就是天线号
			String antenna = StringUtil.toAntenna_no(receiveMessage);
			// U,P,XXX
			String receiveText = StringUtil.toReceiveMessage(receiveMessage);
			logger.info("myUDPReceive :"+receiveMessage);
			// map.put(antenna, receiveText);
			if (StringUtil.toType(receiveText).equals("P"))
			{   
				String antennaP = StringUtil.toantennaP(antenna);
				if(map.containsKey(antennaP))
				{
				MyRunnable handle = (MyRunnable) map.get(antennaP);
				handle.sendMsg(receiveText);
				//get到map后就將對應的k   remove
				map.remove(antennaP);
				}
			} else if (StringUtil.toType(receiveText).equals("X")) 
			{   
				String antennaX = StringUtil.toantennaX(antenna);
				if(map.containsKey(antennaX)) 
				{
				MyProgramRunnable programhandle = (MyProgramRunnable) map.get(antennaX);
				programhandle.sendMsg(receiveText);
				//get到map后就將對應的k    remove
				map.remove(antennaX);
				}
				
			} else if (StringUtil.toType(receiveText).equals("S"))
			{   
				String antennaS = StringUtil.toantennaS(antenna);
				if(map.containsKey(antennaS))
				{
				MySatelliteRunnable satellitehandle = (MySatelliteRunnable) map.get(antennaS);
				satellitehandle.sendMsg(receiveText);
				//get到map后就將對應的k    remove
				map.remove(antennaS);
				}
			}

			logger.info("map中所存数据 ："+map);
			logger.info("myUDPReceive 接受次数 ：" + num++);
			socket.close();
		}

	}

}
