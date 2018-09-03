package com.zxhy.webservice.ws;

import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.zxhy.webservice.entity.UpdateInfo;
@WebService
public interface UpgradeVersion {
	/**
	 * 根据传入的一组天线号，获取他们的IP和port，并向他们多线程发送content
	 * 
	 * @param antennas
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	public void SendUpdateInfo(UpdateInfo info) throws IOException;

}
