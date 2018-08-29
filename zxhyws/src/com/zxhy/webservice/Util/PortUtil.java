package com.zxhy.webservice.Util;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.UnknownHostException;

public class PortUtil {
	  /*** 
     * 测试主机Host的port端口是否被使用
     * @param host 
     * @param port 
     * @throws UnknownHostException  
     */  
    public static boolean isPortUsing(int port) {  
        boolean flag = false;  
       
        try {  
        	
        	 ServerSocket socket = new  ServerSocket(port);  //建立一个Socket连接
           
            flag = true;  
        } catch (IOException e) {  

        }
      
        return flag;  
    }  
    
  
}  

