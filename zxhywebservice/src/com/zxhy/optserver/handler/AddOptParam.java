package com.zxhy.optserver.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.zxhy.optserver.entity.OptParamInfo;
import com.zxhy.optserver.util.JDBCUtils;

public class AddOptParam
{
	
	//传入参数字符串，返回成功添加记录的modem编号，失败返回""
	public String addParam(String paramStr)
	{
		OptParamInfo optInfo = new OptParamInfo();
		
		try
		{
			Map<String,String> optMap = new HashMap<String,String>();
			String[] params = paramStr.split(",");
			for (int i = 0; i <params.length ; i++ ) 
			{
			    String[] mapValue = params[i].split("=");
			    optMap.put(mapValue[0].trim(),mapValue[1].trim());
			} 
			
			optInfo.OPTIONS_FILE_modem_sn = optMap.get("OPTIONS_FILE_modem_sn");
			optInfo.ANTENNA_addr = optMap.get("ANTENNA_addr");
			optInfo.ANTENNA_port = optMap.get("ANTENNA_port");
			optInfo.ETH0_1_address = optMap.get("ETH0_1_address");
			optInfo.ETH0_1_netmask = optMap.get("ETH0_1_netmask");
			optInfo.ETH0_174_address = optMap.get("ETH0_174_address");
			optInfo.ETH0_174_netmask = optMap.get("ETH0_174_netmask");
			optInfo.ETH0_175_address = optMap.get("ETH0_175_address");
			optInfo.ETH0_175_netmask = optMap.get("ETH0_175_netmask");
			optInfo.ETH0_176_address = optMap.get("ETH0_176_address");
			optInfo.ETH0_176_netmask = optMap.get("ETH0_176_netmask");
			optInfo.ETH0_176_rip_enabled = Integer.parseInt(optMap.get("ETH0_176_rip_enabled"));
			optInfo.ETH0_176_web_server_enabled = Integer.parseInt(optMap.get("ETH0_176_web_server_enabled"));
			optInfo.LAN_lan_gw_ip = optMap.get("LAN_lan_gw_ip");
			optInfo.LAN_lan_ip = optMap.get("LAN_lan_ip");
			optInfo.LAN_lan_subnet_ip = optMap.get("LAN_lan_subnet_ip");
			optInfo.MODEM_PARAMETERS_rx_freq = optMap.get("MODEM_PARAMETERS_rx_freq");
			optInfo.MODEM_PARAMETERS_rx_symrate = optMap.get("MODEM_PARAMETERS_rx_symrate");
			optInfo.SATELLITE_hunt_bandwidth = optMap.get("SATELLITE_hunt_bandwidth");
			optInfo.SATELLITE_hunt_frequency = optMap.get("SATELLITE_hunt_frequency");
			optInfo.SATELLITE_longitude = optMap.get("SATELLITE_longitude");
			optInfo.SATELLITE_name = optMap.get("SATELLITE_name");
			optInfo.SATELLITE_polarity = optMap.get("SATELLITE_polarity");
			optInfo.SATELLITE_rx_lcl_osc = optMap.get("SATELLITE_rx_lcl_osc");
			optInfo.VLAN_vid = Integer.parseInt(optMap.get("VLAN_vid"));
			
			//添加参数，有则更新
			int line = optAdd(optInfo);
			if(line > 0)
			{
				return optInfo.OPTIONS_FILE_modem_sn;
			}
			
		}
		catch (Exception exp)
		{
			System.out.println("[ERROR] "+exp);
		}
		
		
		return "";
	}
	
	
	private int optAdd(OptParamInfo optInfo) 
	{	
		String updateTime = DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).format(new java.util.Date());
		try 
		{
			Connection conn = JDBCUtils.getConnection();
			String sql = "INSERT INTO tbl_optparam VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
					+ "ANTENNA_addr=?,ANTENNA_port=?,ETH0_1_address=?,ETH0_1_netmask=?,ETH0_174_address=?,ETH0_174_netmask=?,ETH0_175_address=?,ETH0_175_netmask=?,ETH0_176_address=?,"
					+"ETH0_176_netmask=?,ETH0_176_rip_enabled=?,ETH0_176_web_server_enabled=?,LAN_lan_gw_ip=?,LAN_lan_ip=?,LAN_lan_subnet_ip=?,MODEM_PARAMETERS_rx_freq=?,MODEM_PARAMETERS_rx_symrate=?,"
					+"SATELLITE_hunt_bandwidth=?,SATELLITE_hunt_frequency=?,SATELLITE_longitude=?,SATELLITE_name=?,SATELLITE_polarity=?,SATELLITE_rx_lcl_osc=?,VLAN_vid=?,updateTime=?";
		
		    //执行SQL语句
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, optInfo.OPTIONS_FILE_modem_sn);
			ps.setString(2, optInfo.ANTENNA_addr);
			ps.setString(3, optInfo.ANTENNA_port);
			ps.setString(4, optInfo.ETH0_1_address);
			ps.setString(5, optInfo.ETH0_1_netmask);
			ps.setString(6, optInfo.ETH0_174_address);
			ps.setString(7, optInfo.ETH0_174_netmask);
			ps.setString(8, optInfo.ETH0_175_address);
			ps.setString(9, optInfo.ETH0_175_netmask);
			ps.setString(10, optInfo.ETH0_176_address);
			ps.setString(11, optInfo.ETH0_176_netmask);
			ps.setInt(12, optInfo.ETH0_176_rip_enabled);
			ps.setInt(13, optInfo.ETH0_176_web_server_enabled);
			ps.setString(14, optInfo.LAN_lan_gw_ip);
			ps.setString(15, optInfo.LAN_lan_ip);
			ps.setString(16, optInfo.LAN_lan_subnet_ip);
			ps.setString(17, optInfo.MODEM_PARAMETERS_rx_freq);
			ps.setString(18, optInfo.MODEM_PARAMETERS_rx_symrate);
			ps.setString(19, optInfo.SATELLITE_hunt_bandwidth);
			ps.setString(20, optInfo.SATELLITE_hunt_frequency);
			ps.setString(21, optInfo.SATELLITE_longitude);
			ps.setString(22, optInfo.SATELLITE_name);
			ps.setString(23, optInfo.SATELLITE_polarity);
			ps.setString(24, optInfo.SATELLITE_rx_lcl_osc);
			ps.setInt(25, optInfo.VLAN_vid);
			ps.setString(26, updateTime);
			ps.setString(27, optInfo.ANTENNA_addr);
			ps.setString(28, optInfo.ANTENNA_port);
			ps.setString(29, optInfo.ETH0_1_address);
			ps.setString(30, optInfo.ETH0_1_netmask);
			ps.setString(31, optInfo.ETH0_174_address);
			ps.setString(32, optInfo.ETH0_174_netmask);
			ps.setString(33, optInfo.ETH0_175_address);
			ps.setString(34, optInfo.ETH0_175_netmask);
			ps.setString(35, optInfo.ETH0_176_address);
			ps.setString(36, optInfo.ETH0_176_netmask);
			ps.setInt(37, optInfo.ETH0_176_rip_enabled);
			ps.setInt(38, optInfo.ETH0_176_web_server_enabled);
			ps.setString(39, optInfo.LAN_lan_gw_ip);
			ps.setString(40, optInfo.LAN_lan_ip);
			ps.setString(41, optInfo.LAN_lan_subnet_ip);
			ps.setString(42, optInfo.MODEM_PARAMETERS_rx_freq);
			ps.setString(43, optInfo.MODEM_PARAMETERS_rx_symrate);
			ps.setString(44, optInfo.SATELLITE_hunt_bandwidth);
			ps.setString(45, optInfo.SATELLITE_hunt_frequency);
			ps.setString(46, optInfo.SATELLITE_longitude);
			ps.setString(47, optInfo.SATELLITE_name);
			ps.setString(48, optInfo.SATELLITE_polarity);
			ps.setString(49, optInfo.SATELLITE_rx_lcl_osc);
			ps.setInt(50, optInfo.VLAN_vid);
			ps.setString(51, updateTime);

		    int line = ps.executeUpdate();
		    return line;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}

}
