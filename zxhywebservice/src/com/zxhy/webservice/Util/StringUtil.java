package com.zxhy.webservice.Util;

public class StringUtil {
	/**
	 * 从前端传入的content中截取GUID
	 * @param content
	 * @return
	 */
	public static String toGuid(String content) {

		String[] s = content.split("\n");
		String GUID = s[1].substring(7);
		return GUID;
	}
	
	public static String toAntenna_no(String receive) {
		String[] s = receive.split(",");
		String receiveMessage = s[0];
		return receiveMessage;
	}
	public static String toReceiveMessage(String receive) {
		String[] s = receive.split(",",2);
		String receiveMessage = s[1];
		return receiveMessage;
	}
	
	
	/**
	 * U,P,content
	 * @param content
	 * @return
	 */
	public static String toMessage(String content) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("U,");
		strBuffer.append("P,");
		strBuffer.append(content);
		String Message = strBuffer.toString();
		
		return Message;
	}
	
	public static String toText(String content) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("U,");
		strBuffer.append("S,");
		strBuffer.append(content);
		String text = strBuffer.toString();
		return text;
	}
	
	/**
	 * U,P,GUID
	 * @param content
	 * @return
	 */
	public static String toGUID(String content) {
		String guid = StringUtil.toGuid(content);
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("U,");
		strBuffer.append("P,");
		strBuffer.append(guid);
		String GUID = strBuffer.toString();
		
		return GUID;
		
	}
	
	
	/**
	 * U,X,AMC,VERSION,SIZE,size
	 * @param AMC
	 * @param VERSION
	 * @param SIZE
	 * @param size
	 * @return
	 */
	public static String toUpgrateProgram(String AMC,String VERSION,String SIZE,String size) {
		
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("U,");
		strBuffer.append("X,");
		strBuffer.append(AMC);
		strBuffer.append(VERSION);
		strBuffer.append(SIZE);
		strBuffer.append(size);
		
		String message = strBuffer.toString();
		
		return message;
		
	}

}
