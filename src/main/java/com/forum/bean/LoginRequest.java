package com.forum.bean;

import java.io.Serializable;

public class LoginRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4324805150902975427L;

	private String username ;
	
	private String password ;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
