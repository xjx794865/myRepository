package com.zxhy.webservice.ws;

import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UpgradeVersion {
	/**
	 * ���ݴ�������ߺŻ�ȡ��Ӧ��IP��port��������content
	 * 
	 * @param antenna_no
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void insertAndSendByNo(String antenna_no, String content) throws IOException;

	/**
	 * ���ݴ����һ�����ߺţ���ȡ���ǵ�IP��port���������Ƕ��̷߳���content
	 * 
	 * @param antennas
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void insertAllAndSend(String[] antennas, String content) throws IOException;

		

	/**
	 * �����һ�����ߺ��ն˷���"U,X,AMC,VERSION,SIZE,size",�����ܻ�Ӧ
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
	 * �����һ�����ߺŷ���"U,X,AMC,VERSION,SIZE,size",�����ܻ�Ӧ
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
