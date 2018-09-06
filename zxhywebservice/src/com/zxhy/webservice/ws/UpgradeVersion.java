package com.zxhy.webservice.ws;

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.zxhy.optserver.entity.TermCategoryInfo;
import com.zxhy.optserver.entity.TermDetailInfo;
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
	void SendUpdateInfo(UpdateInfo info) throws IOException;
	
	//OPT参数入库
	@WebMethod
	List<String> addOptParams(List<String> paramList);
		
	//获取终端分组
	@WebMethod
	List<TermCategoryInfo> getTermCategory(String category);
		
	//获取终端详细信息（参数升级）
	@WebMethod
	List<TermDetailInfo> getTermDetailList(String categoryGroupNo);
		
	//获取机型代码
	@WebMethod
	List<String> getTermModel();

}
