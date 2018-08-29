package com.zxhy.webservice.entity;

import java.util.Date;

public class Parameters {
	
	private Integer GroupId;
	
	private double SVN;
	
	private Date DatetTime;
	
	private String content;
	
	private Integer currentId;

	public Integer getGroupId() {
		return GroupId;
	}

	public void setGroupId(Integer groupId) {
		GroupId = groupId;
	}

	public double getSVN() {
		return SVN;
	}

	public void setSVN(double sVN) {
		SVN = sVN;
	}

	public Date getDatetTime() {
		return DatetTime;
	}

	public void setDatetTime(Date datetTime) {
		DatetTime = datetTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCurrentId() {
		return currentId;
	}

	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}
	
	

}
