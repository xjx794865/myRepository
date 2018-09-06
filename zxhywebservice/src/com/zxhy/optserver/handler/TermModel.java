package com.zxhy.optserver.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxhy.optserver.util.DebugUtil;
import com.zxhy.optserver.util.JDBCUtils;

public class TermModel 
{
	public List<String> GetTermModelList()
    {
  	  List<String> termModelList = new ArrayList<String>();
  	  Connection conn = JDBCUtils.getConnection();
	  String sql = "SELECT CategoryGroupNo FROM tbl_terminals WHERE CategoryGroupNo!=\"\"  GROUP BY CategoryGroupNo";
	
	  try 
	  {
			PreparedStatement stat= conn.prepareStatement(sql);
			ResultSet rs = stat.executeQuery();
			
		    while(rs.next()) 
		    {
		    	String modelNo = rs.getString("CategoryGroupNo");
		    	termModelList.add(modelNo);
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
	  
      return termModelList;
    }
}
