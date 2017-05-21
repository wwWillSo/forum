package com.forum.bean;

import java.io.Serializable;

public class LoginResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716552919527274285L;

	private String statusCode ;
	
	private String desc ;
	
	private String redirectUrl ;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
