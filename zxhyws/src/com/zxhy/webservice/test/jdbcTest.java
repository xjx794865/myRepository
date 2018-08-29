package com.zxhy.webservice.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Ignore;
import org.junit.Test;

import com.zxhy.webservice.Util.InetAddressUtils;
import com.zxhy.webservice.Util.JDBCUtils;
import com.zxhy.webservice.Util.PidUtils;
import com.zxhy.webservice.entity.Area;

public class jdbcTest {
	
		@Test
	    @Ignore
		public void method(){
			try{
				//获取QueryRunner 
				QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
				//执行SQL语句
				String sql = "SELECT * FROM tb_area WHERE area_id=?";
				Object[] params = {1};
				
				Area zw = qr.query( sql, new BeanHandler<Area>(Area.class), params);
				//结果集处理
				System.out.println(zw.toString());
				System.out.println(zw.getAreaName());
				
			} catch(SQLException e){
				throw new RuntimeException(e);
			}
		}
		
		
	/*
		public static void update(Area area) {
			
			
			
			try {
				QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "INSERT INTO tb_area(area_name,priority) VALUES(?,?)";			
				Object[] params = {area.getAreaName(), area.getPriority()};	
				 
				  int line = qr.update(sql,params);
			
			//结果集的处理
			System.out.println("line="+line);
			
			
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		public static void main(String[] args) {
			Area area = new Area();
			area.setAreaName("zxhy");
			area.setPriority(10);
			jdbcTest.update(area);
		}
	
		*/
		@Test
		@Ignore
		public void fun() throws SQLException, IOException {
			Connection conn = JDBCUtils.getConnection();
			String sql = "UPDATE tb_upgraderecord SET Pid = 1 WHERE antenna_no=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, "002");
			int line = pstat.executeUpdate();
			conn.commit();
			System.out.println(line);
		
			pstat.close();
			conn.close();
			}
		@Test
		@Ignore
		public void fun1() throws IOException {
			String antenna_no = "004";
			String ip =InetAddressUtils.returnAddress(antenna_no);
			System.out.println(ip);
			
		}
		@Test
		@Ignore
		public void fun2() throws SQLException, IOException {
			
			String[] antenna = PidUtils.selectAntennaByPid();
			
		for(int i = 0; i < antenna.length;i++) {	
		System.out.println(antenna[i]);
		}
			
		}
		
		@Test
		public void fun3() throws IOException, SQLException {
			
			String antenna_no = "001";
			
			int pid = PidUtils.selectPidbyAntenna(antenna_no);
			
			System.out.println(pid);
		}

	}



