package com.zxhy.webservice.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.zxhy.optserver.entity.TermCategoryInfo;
import com.zxhy.optserver.entity.TermDetailInfo;
import com.zxhy.webservice.entity.UpdateInfo;

@WebService
public interface UpgradeVersion {
	/**
	 * ����һ��UpdateInfo���͵Ķ���info info���� cmd��terms��content
	 * @param info
	 *
	 */
	@WebMethod
	void SendUpdateInfo(UpdateInfo info);
	
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
