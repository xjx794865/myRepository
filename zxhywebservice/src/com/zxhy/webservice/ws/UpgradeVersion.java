package com.zxhy.webservice.ws;

import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.zxhy.webservice.entity.UpdateInfo;
@WebService
public interface UpgradeVersion {
	/**
	 * ���ݴ����һ�����ߺţ���ȡ���ǵ�IP��port���������Ƕ��̷߳���content
	 * 
	 * @param antennas
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void SendUpdateInfo(UpdateInfo info) throws IOException;

}
