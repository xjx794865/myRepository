package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUIDUtil {
	
	/**
     * ����tbl_upgradecontent��GUID�������е�GUID����.
     * @return
     * @throws SQLException
     * @throws IOException 
     */
	public static String[] selectGUID() 
	{
		String[] arr = null;
		try {
		Connection conn = JDBCUtils.getConnection();
		List<String> list = new ArrayList<String>();

		String sql = "SELECT * from tbl_upgradecontent";

		PreparedStatement psata = conn.prepareStatement(sql);

		ResultSet rs = psata.executeQuery();

		while (rs.next())
		{
			list.add(rs.getString("GUID"))  ;

		}
		
		//��list<String>ת��string[].
		 arr = list.toArray(new String[list.size()]);
	
		 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	

}
