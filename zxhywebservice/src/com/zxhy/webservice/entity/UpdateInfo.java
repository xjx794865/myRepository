package com.zxhy.webservice.entity;

import java.util.List;

public class UpdateInfo {
	public String cmd;
	public String content;
	public List<String> terms;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getTerms() {
		return terms;
	}
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
	
	
	
}
