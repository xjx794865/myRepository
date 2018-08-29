package com.zxhy.webservice.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Object2Array {
	
	/**
	 * ����תbyte����
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();
			
		}catch(IOException e) {
			e.printStackTrace();
			
		}finally {
			if(objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			}
			if(byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return bytes;
		
	}
	
	
	  /**
	   * Byte����ת����
	   * @param bytes
	   * @return
	   */
	 public static Object byteArrayToObject(byte[] bytes) {
		 Object obj = null;
		 ByteArrayInputStream byteArrayInputStream = null;
		 ObjectInputStream objectInputStream = null;
		 try {
			 byteArrayInputStream = new ByteArrayInputStream(bytes);
			 objectInputStream = new ObjectInputStream(byteArrayInputStream);
			 obj = objectInputStream.readObject();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
			 
		 }finally {
			 if(byteArrayInputStream != null) {
				 try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			 }
			 if(objectInputStream != null) {
				 try {
					objectInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			 }
		 }
		 
		 return obj;
	 }
	

}