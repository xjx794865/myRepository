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
	
	/**
	 * 终端号
	 * @param receive
	 * @return
	 */
	public static String toAntenna_no(String receive) {
		String[] s = receive.split(",");
		String receiveMessage = s[0];
		return receiveMessage;
	}
	
	/**
	 * U,P,XXX
	 * @param receive
	 * @return
	 */
	public static String toReceiveMessage(String receive) {
		String[] s = receive.split(",",2);
		String receiveMessage = s[1];
		return receiveMessage;
	}
	
	/**
	 * U,X,XXXX
	 * X
	 * @param receive
	 * @return
	 */
	public static String toType(String receive) {
		String[] s = receive.split(",");
		String receiveMessage = s[1];
		return receiveMessage;
	}
	
	
	
	/**
	 * 程序升级 生成guid
	 * @param antenna
	 * @param content
	 * @return
	 */
	public static String toProgram(String content) {
		String[] s = content.split(",");
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(s[2]);
		strBuffer.append(s[3]);
		String Message = strBuffer.toString();
		
		return Message;
		
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
	/**
	 * U,S,content
	 * @param content
	 * @return
	 */
	public static String toText(String content) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("U,");
		strBuffer.append("S,");
		strBuffer.append(content);
		String text = strBuffer.toString();
		return text;
	}
	/**
	 * U,X,content
	 * @param content
	 * @return
	 */
	public static String toProgramContent(String content) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("U,");
		strBuffer.append("X,");
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
	/**
	 * XXXXP
	 * @param antenna
	 * @return
	 */
	public static String toantennaP(String antenna){

		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(antenna);
		strBuffer.append("P");
		
		String antennaP = strBuffer.toString();
		
		return antennaP;
	}
	
	/**
	 * xxxxP
	 * @param antenna
	 * @return
	 */
	public static String toantennaX(String antenna) {
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(antenna);
		strBuffer.append("X");
		
		String antennaX = strBuffer.toString();
		
		return antennaX;
		
	}
	
	/**
	 * xxxxS
	 * @param antenna
	 * @return
	 */
	public static String toantennaS(String antenna ) {
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(antenna);
		strBuffer.append("S");
		
		String antennaS = strBuffer.toString();
		
		return antennaS;
	}

}
