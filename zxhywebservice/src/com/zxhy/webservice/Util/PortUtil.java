package com.zxhy.webservice.Util;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.UnknownHostException;

public class PortUtil {
	  /*** 
     * ��������Host��port�˿��Ƿ�ʹ��
     * @param host 
     * @param port 
     * @throws UnknownHostException  
     */  
    public static boolean isPortUsing(int port) {  
        boolean flag = false;  
       
        try {  
        	
        	 ServerSocket socket = new  ServerSocket(port);  //����һ��Socket����
           
            flag = true;  
        } catch (IOException e) {  

        }
      
        return flag;  
    }  
    
  
}  

