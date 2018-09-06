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
	 * ���ݴ����һ�����ߺţ���ȡ���ǵ�IP��port���������Ƕ��̷߳���content
	 * 
	 * @param antennas
	 * @param content
	 * @throws IOException
	 */
	@WebMethod
	void SendUpdateInfo(UpdateInfo info) throws IOException;
	
	//OPT�������
	@WebMethod
	List<String> addOptParams(List<String> paramList);
		
	//��ȡ�ն˷���
	@WebMethod
	List<TermCategoryInfo> getTermCategory(String category);
		
	//��ȡ�ն���ϸ��Ϣ������������
	@WebMethod
	List<TermDetailInfo> getTermDetailList(String categoryGroupNo);
		
	//��ȡ���ʹ���
	@WebMethod
	List<String> getTermModel();

}
