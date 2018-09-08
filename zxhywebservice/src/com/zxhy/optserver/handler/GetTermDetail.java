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
	  String sql = "SELECT 船号, 终端号, 地区, 版本, status, 卫星, 经度, 维度, 代理商  FROM view_termdetail WHERE 分组号=?";
	
	  try 
	  {
			PreparedStatement stat= conn.prepareStatement(sql);
			stat.setString(1, categoryGroupNo);
			ResultSet rs = stat.executeQuery();
			
		    while(rs.next()) 
		    {
		    	String position = "";
		    	float lng = rs.getFloat("经度");
		    	float lat = rs.getFloat("维度");
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
	    	    termInfo.boatName = rs.getString("船号");
	    	    termInfo.termNo = rs.getString("终端号");
	    	    termInfo.district = rs.getString("地区");
	    	    termInfo.version = rs.getString("版本");
	    	    if(rs.getInt("status") == 1)
	    	    {
	    	    	termInfo.status = "在线";
	    	    }
	    	    else
	    	    {
	    	    	termInfo.status = "离线";
	    	    }
	    	    termInfo.satellite = rs.getString("卫星");
	    	    termInfo.position = position;
	    	    termInfo.belong = rs.getString("代理商");
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
