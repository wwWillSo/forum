package com.forum.bean;

import java.io.Serializable;

import com.forum.entity.Comment;

public class CommentBean extends Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 401188367215967189L;
	private String loginName ;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
}
