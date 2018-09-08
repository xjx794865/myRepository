package com.zxhy.webservice.ws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zxhy.optserver.entity.TermCategoryInfo;
import com.zxhy.optserver.entity.TermDetailInfo;
import com.zxhy.optserver.handler.AddOptParam;
import com.zxhy.optserver.handler.GetTermCategoryList;
import com.zxhy.optserver.handler.GetTermDetail;
import com.zxhy.optserver.handler.TermModel;
import com.zxhy.webservice.Util.JDBCUtils;
import com.zxhy.webservice.Util.MyUDPReceive;
import com.zxhy.webservice.Util.PortUtil;
import com.zxhy.webservice.Util.StringUtil;
import com.zxhy.webservice.Util.ThreadPool;
import com.zxhy.webservice.Util.isSuccessUpgradeThread;
import com.zxhy.webservice.entity.UpdateInfo;

@WebService
public class UpgradeVersionImpl implements UpgradeVersion {

	Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
    
	//构造器 用于开启接受线程
	public UpgradeVersionImpl() 
	{
		Properties props = new Properties();
		try {
			props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        
		//如果接受线程已经被启动 将不开启
		if (PortUtil.isPortUsing(8900)) 
		{ 
		System.out.println("Start UDP revieve thread...");
		new Thread(new MyUDPReceive()).start();
		}
		
		Thread daemonThread = new Thread(new isSuccessUpgradeThread());
		daemonThread.setDaemon(true);
		System.out.println("Start daemonThread...");
		daemonThread.start();

	}

	/**
	 * 
	 *  @param info
	 *  @throws IOException
	 */
	@Override
	public void SendUpdateInfo(UpdateInfo info)  {
		switch (info.cmd) {
		case "P_UPDATE":
			System.out.println(info.cmd + ":添加数据中");
			UpdateInfoInitialize(info);
			/*
			try {
				System.out.println("start threadpool...");
				//参数升级线程池
				ThreadPool.ExecutorCompletionService(info);

			} catch (Exception e) {
				logger.error(e.getMessage()+"参数升级线程池");
		
			}
			*/
			break;
		case "X_UPDATE":
			System.out.println(info.cmd +":添加数据中");
			UpdateInfoProgram(info);
			/*
			try {
				//程序升级线程池
				ThreadPool.ExectorCompletionServiceForUpgrate(info);
			} catch (Exception ex) {
				logger.error(ex.getMessage());

			}
			*/
			break;
		case "S_UPDATE":
			System.out.println(info.cmd + ":添加数据中");
			//卫星参数线程池
			UpdateInfoSatellite(info);
			/*
			try 
			{
			ThreadPool.ExectorCompletionServiceForSatellite(info);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
			*/
            break;
		default:
			System.out.println("unknown update command: " + info.cmd);
		}
	}
		
    //参数升級
	private void UpdateInfoInitialize(UpdateInfo info) {
		//Boolean isGUID = true;
		String GUID = StringUtil.toGuid(info.content);
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstat = null;
		
		
		try 
		{	  
			
			// 关闭自动提交 打开事务
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO 	tbl_upgradecontent(GUID,content,dateTime) VALUES(?,?,?) ON DUPLICATE KEY UPDATE content=?,dateTime=?";
			pstat = conn.prepareStatement(sql1);

			pstat.setString(1, GUID);
			pstat.setString(2, info.content);
			// 将util中的date与sql的date相匹配
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstat.setTimestamp(3, timestamp);
			pstat.setString(4, info.content);
			pstat.setTimestamp(5, timestamp);
			pstat.executeUpdate();
			
			
			String sql2 =  "INSERT INTO tbl_upgraderecord(antenna_no,GUID,successUpgrade) VALUES(?,?,?)";
			PreparedStatement pstat2 = conn.prepareStatement(sql2);
            // sql批处理
			for (String antenna_no : info.terms)
            {
				pstat2.setString(1, antenna_no);
				pstat2.setString(2, GUID);
				pstat2.setInt(3, 0);
				pstat2.addBatch();
			}
			pstat2.executeBatch();
				
			conn.commit();
			pstat2.close();
			pstat.close();
			
		}
		catch (Exception e)
		{
			try 
			{
				conn.rollback();
			} 
			catch (SQLException e1) 
			{
				logger.error(e1.getMessage());

			}
			logger.error(e.getMessage());
		}
		finally
		{
			try 
			{   
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		//释放一个信号量
		isSuccessUpgradeThread.recvSemophore.release();
	}
	
	    //程序升級
		private void UpdateInfoProgram(UpdateInfo info)
		{
				String GUID = StringUtil.toProgram(info.content);
				Connection conn = null;
				PreparedStatement pstat = null;
				
				try 
				{
					conn = JDBCUtils.getConnection();
				} catch (Exception e2) 
				{
		          e2.printStackTrace();
				}
				try {
					conn.setAutoCommit(false);
					String sql2 = "INSERT INTO 	tbl_programerupgradecontent(GUID,content,dateTime) VALUES(?,?,?) ON DUPLICATE KEY UPDATE content=?,dateTime=?";
		            pstat = conn.prepareStatement(sql2);
					pstat.setString(1, GUID);
					pstat.setString(2, info.content);
					// 将util中的date与sql的date相匹配
					Date date = new Date();
					Timestamp timestamp = new Timestamp(date.getTime());
					pstat.setTimestamp(3, timestamp);
					pstat.setString(4, info.content);
					pstat.setTimestamp(5, timestamp);
					pstat.executeUpdate();
					
					
					String sql1 = "INSERT INTO tbl_programerupgraderecord(antenna_no,GUID,successUpgrade) VALUES(?,?,?)";
					PreparedStatement pstat2 = conn.prepareStatement(sql1);

					// sql批处理
					for (String antenna_no :info.terms) 
					{
						pstat2.setString(1, antenna_no);
						pstat2.setString(2, GUID);
						pstat2.setInt(3, 0);
						pstat2.addBatch();
					}
					pstat2.executeBatch();

					conn.commit();
					pstat.close();
					pstat2.close();
				    
					
				} catch (Exception e) {
					try 
					{
					  conn.rollback();
					} catch (SQLException e1)
					{
						logger.error(e1.getMessage());
					}
					
					logger.error(e.getMessage());
					
				}
				finally {
					
					try {
						conn.setAutoCommit(true);
						conn.close();
					} catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
				}
				//释放一个信号量
				isSuccessUpgradeThread.recvSemophore.release();
			}
	    //卫星参数
	    private void UpdateInfoSatellite(UpdateInfo info) {
		      String GUID = StringUtil.toGuid(info.content);
		      Connection  conn = JDBCUtils.getConnection();
		      PreparedStatement pstat2 = null;
	
	        try 
		    {
			  // 关闭自动提交 打开事务
			  conn.setAutoCommit(false);
			  String sql1 = "INSERT INTO tbl_satupgradecontent(GUID,content,dateTime) VALUES(?,?,?) ON DUPLICATE KEY UPDATE content=?,dateTime=?";
			  pstat2 = conn.prepareStatement(sql1);
			  pstat2.setString(1, GUID);
			  pstat2.setString(2, info.content);
			  // 将util中的date与sql的date相匹配
			  Date date = new Date();
		      Timestamp timestamp = new Timestamp(date.getTime());
			  pstat2.setTimestamp(3, timestamp);
			  pstat2.setString(4, info.content);
			  pstat2.setTimestamp(5, timestamp);
			  pstat2.executeUpdate();
			
			
			  String sql2 = "INSERT INTO  tbl_satupgraderecord(antenna_no,GUID,successUpgrade) VALUES(?,?,?)";
			  PreparedStatement pstat = conn.prepareStatement(sql2);
			  // sql批处理
			  for (String antenna_no : info.terms) 
			  {
				pstat.setString(1, antenna_no);
				pstat.setString(2, GUID);
				pstat.setInt(3, 0);
				pstat.addBatch();
			  }
			  pstat.executeBatch();

			  conn.commit();
			  pstat2.close();
			  pstat.close();
		    } catch (Exception e) 
		   
	     {
			try 
			{
				conn.rollback();
			} catch (SQLException e1)
			{
				logger.error(e1.getMessage());
			}
			logger.error(e.getMessage());
			
		}
		finally
		{
			try 
			{
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e)
			{
		
				e.printStackTrace();
			}
		}   
	        //释放一个信号量
	        isSuccessUpgradeThread.recvSemophore.release();
	}
	
	//opt   reference
	@Override
	public List<String> addOptParams(List<String> paramsList)
	{
		String[] params = paramsList.toArray(new String[paramsList.size()]);
		List<String> addResults =  new ArrayList<String>();
		for(int i = 0; i<params.length; i++)
		{
			System.out.println(params[i]);
			AddOptParam addOpt = new AddOptParam();
			String successModem = addOpt.addParam(params[i]);
			if(!((successModem == null) || (successModem.equals(""))))
			{
				addResults.add(successModem);
			}
		}
		
		return addResults;
	}
	
	@Override
	public List<TermCategoryInfo> getTermCategory(String category)
	{
		GetTermCategoryList getcategory = new GetTermCategoryList();
		List<TermCategoryInfo> categoryList = getcategory.getCategoryList(category);
		
		return categoryList;
	}
	
	@Override
	public List<TermDetailInfo> getTermDetailList(String categoryGroupNo)
	{
		GetTermDetail getDetail = new GetTermDetail();
		List<TermDetailInfo> termDetailList = getDetail.getDetailList(categoryGroupNo.trim());
		
		return termDetailList;
	}
	
	@Override
	public List<String> getTermModel()
	{
		TermModel termModel = new TermModel();
		List<String> termModels = termModel.GetTermModelList();
		
		return termModels;
	}
	
}
