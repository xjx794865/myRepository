package com.zxhy.webservice.ws;

import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UpgradeVersion {
	/**
	 * 根据传入的天线号获取相应的IP和port，并发送content
	 * 
	 * @param antenna_no
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void insertAndSendByNo(String antenna_no, String content) throws IOException;

	/**
	 * 根据传入的一组天线号，获取他们的IP和port，并向他们多线程发送content
	 * 
	 * @param antennas
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void insertAllAndSend(String[] antennas, String content) throws IOException;

		

	/**
	 * 向传入的一个天线号终端发送"U,X,AMC,VERSION,SIZE,size",并接受回应
	 * 
	 * @param antenna_no
	 * @param AMC
	 * @param VERSION
	 * @param SIZE
	 * @param size
	 * @throws IOException
	 */
	@WebMethod
	public void sendForUpgrateProgram(String antenna_no, String AMC, String VERSION, String SIZE, String size)
			throws IOException;

	/**
	 * 向传入的一组天线号发送"U,X,AMC,VERSION,SIZE,size",并接受回应
	 * @param antenna_no
	 * @param AMC
	 * @param VERSION
	 * @param SIZE
	 * @param size
	 * @throws IOException
	 */
	@WebMethod
	public void sendAllForUpgrateProgram(String[] antenna_no, String AMC, String VERSION, String SIZE, String size)
			throws IOException;

}
