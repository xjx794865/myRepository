package com.zxhy.optserver.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxhy.optserver.entity.TermDetailInfo;
import com.zxhy.optserver.util.DebugUtil;
import com.zxhy.optserver.util.JDBCUtils;

public class GetTermDetail 
{
	public List<TermDetailInfo> getDetailList(String categoryGroupNo)
    {
  	  List<TermDetailInfo> termInfoList = new ArrayList<TermDetailInfo>();
  	  Connection conn = JDBCUtils.getConnection();
	  String sql = "SELECT ����, �ն˺�, ����, �汾, status, ����, ����, ά��, ������  FROM view_termdetail WHERE �����=?";
	
	  try 
	  {
			PreparedStatement stat= conn.prepareStatement(sql);
			stat.setString(1, categoryGroupNo);
			ResultSet rs = stat.executeQuery();
			
		    while(rs.next()) 
		    {
		    	String position = "";
		    	float lng = rs.getFloat("����");
		    	float lat = rs.getFloat("ά��");
		    	if(lng >= 0)
		    	{
		    		position = position + "[E:" + lng + "] ";
		    	}
		    	else 
		    	{
		    		position = position + "[W:" + Math.abs(lng) + "] ";
		    	}
		    	
		    	if(lat >= 0)
		    	{
		    		position = position + "[N:" + lat + "]";
		    	}
		    	else 
		    	{
		    		position = position + "[S:" + Math.abs(lat) + "]";
		    	}
		    	
	    	    TermDetailInfo termInfo = new TermDetailInfo();
	    	    termInfo.boatName = rs.getString("����");
	    	    termInfo.termNo = rs.getString("�ն˺�");
	    	    termInfo.district = rs.getString("����");
	    	    termInfo.version = rs.getString("�汾");
	    	    if(rs.getInt("status") == 1)
	    	    {
	    	    	termInfo.status = "����";
	    	    }
	    	    else
	    	    {
	    	    	termInfo.status = "����";
	    	    }
	    	    termInfo.satellite = rs.getString("����");
	    	    termInfo.position = position;
	    	    termInfo.belong = rs.getString("������");
	    	    termInfoList.add(termInfo);
		    }
		    rs.close();
		    stat.close();
		    conn.close();
	  } 
	  catch (SQLException e)
	  {
		   DebugUtil.addDebug(DebugUtil.getExpMessage(e), DebugUtil.LEVEL_1);
	  }
	  catch(Exception exp)
	  {
		   DebugUtil.addDebug(DebugUtil.getExpMessage(exp), DebugUtil.LEVEL_1);

	  }
	  
      return termInfoList;
    }
}
