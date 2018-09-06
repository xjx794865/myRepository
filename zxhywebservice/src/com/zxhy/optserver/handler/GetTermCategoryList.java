package com.zxhy.optserver.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxhy.optserver.entity.TermCategoryInfo;
import com.zxhy.optserver.util.DebugUtil;
import com.zxhy.optserver.util.JDBCUtils;

public class GetTermCategoryList 
{
      public List<TermCategoryInfo> getCategoryList(String categoryNo)
      {
    	  int categoryNoLength = categoryNo.trim().length();
    	  List<TermCategoryInfo> categoryInfo = new ArrayList<TermCategoryInfo>();
   
    	  switch(categoryNoLength)
    	  {
    	     case 1:
    	    	 if(categoryNo.trim().equals("0"))
    	    	 {
    	    		 categoryInfo = getGenerationList();
    	    	 }
    	    	 else
    	    	 {
    	    		 categoryInfo = getAxisList(categoryNo.trim());
    	    	 }
    	    	 break;
    	     case 2:
	    		 categoryInfo = getTunerList(categoryNo.trim());
    	    	 break;
    	     case 3:
	    		 categoryInfo = getModemList(categoryNo.trim());
    	    	 break;
    	     case 4:
	    		 categoryInfo = getIntergrateList(categoryNo.trim());
    	    	 break;
    	     default:
    	    	 break;
    	  }
    	  return categoryInfo;
      }
      
      
      private List<TermCategoryInfo> getGenerationList()
      {
    	  List<TermCategoryInfo> genarations = new ArrayList<TermCategoryInfo>();
    	  
    	  Connection conn = JDBCUtils.getConnection();
  		  String sql = "SELECT No, generationName FROM tbl_termgenerationcategory";
  		
	  	  try 
	  	  {
	  			PreparedStatement stat= conn.prepareStatement(sql);
	  			ResultSet rs = stat.executeQuery();
	  			
	  		    while(rs.next()) 
	  		    {
	  	    	    TermCategoryInfo generationCategory = new TermCategoryInfo();
	  		    	generationCategory.categoryNo = rs.getString("NO");
	  		    	generationCategory.categoryName = rs.getString("generationName");
	  		    	genarations.add(generationCategory);
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

    	  return genarations;
      }
      
      private List<TermCategoryInfo> getAxisList(String generationNo)
      {
    	  List<TermCategoryInfo> axisInfos = new ArrayList<TermCategoryInfo>();
    	  
    	  Connection conn = JDBCUtils.getConnection();
  		  String sql = "SELECT NO, axisName FROM tbl_termaxiscategory where generationNo=?";
  		
	  	  try 
	  	  {
	  			PreparedStatement stat= conn.prepareStatement(sql);
				stat.setString(1, generationNo);
	  			ResultSet rs = stat.executeQuery();
	  			
	  		    while(rs.next()) 
	  		    {
	  	    	    TermCategoryInfo axisCategory = new TermCategoryInfo();
	  	    	    axisCategory.categoryNo = rs.getString("NO");
	  	    	    axisCategory.categoryName = rs.getString("axisName");
	  		    	axisInfos.add(axisCategory);
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

    	  return axisInfos;
      }
      
      private List<TermCategoryInfo> getTunerList(String axisNo)
      {
    	  List<TermCategoryInfo> tunerInfos = new ArrayList<TermCategoryInfo>();
    	  
    	  Connection conn = JDBCUtils.getConnection();
  		  String sql = "SELECT No, tunerName FROM tbl_termtunercategory where axisNo=?";
  		
	  	  try 
	  	  {
	  			PreparedStatement stat= conn.prepareStatement(sql);
				stat.setString(1, axisNo);
	  			ResultSet rs = stat.executeQuery();
	  			
	  		    while(rs.next()) 
	  		    {
	  	    	    TermCategoryInfo tunerCategory = new TermCategoryInfo();
	  	    	    tunerCategory.categoryNo = rs.getString("No");
	  	    	    tunerCategory.categoryName = rs.getString("tunerName");
	  	    	    tunerInfos.add(tunerCategory);
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

    	  return tunerInfos;
      }
      
      private List<TermCategoryInfo> getModemList(String tunerNo)
      {
    	  List<TermCategoryInfo> modemInfos = new ArrayList<TermCategoryInfo>();
    	  
    	  Connection conn = JDBCUtils.getConnection();
  		  String sql = "SELECT NO, modemName FROM tbl_termmodemcategory where turnerNo=?";
  		
	  	  try 
	  	  {
	  			PreparedStatement stat= conn.prepareStatement(sql);
				stat.setString(1, tunerNo);
	  			ResultSet rs = stat.executeQuery();
	  			
	  		    while(rs.next()) 
	  		    {
	  	    	    TermCategoryInfo modemCategory = new TermCategoryInfo();
	  	    	    modemCategory.categoryNo = rs.getString("NO");
	  	    	    modemCategory.categoryName = rs.getString("modemName");
	  	    	    modemInfos.add(modemCategory);
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

    	  return modemInfos;
      }
      
      private List<TermCategoryInfo> getIntergrateList(String modemNo)
      {
    	  List<TermCategoryInfo> intergrateInfos = new ArrayList<TermCategoryInfo>();
    	  
    	  Connection conn = JDBCUtils.getConnection();
  		  String sql = "SELECT NO, intergrateName FROM tbl_termintergratecategory where modemNo=?";
  		
	  	  try 
	  	  {
	  			PreparedStatement stat= conn.prepareStatement(sql);
				stat.setString(1, modemNo);
	  			ResultSet rs = stat.executeQuery();
	  			
	  		    while(rs.next()) 
	  		    {
	  	    	    TermCategoryInfo intergrateCategory = new TermCategoryInfo();
	  	    	    intergrateCategory.categoryNo = rs.getString("NO");
	  	    	    intergrateCategory.categoryName = rs.getString("intergrateName");
	  	    	    intergrateInfos.add(intergrateCategory);
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

    	  return intergrateInfos;
      }

}
