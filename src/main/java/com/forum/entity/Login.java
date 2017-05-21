package com.forum.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login database table.
 * 
 */
@Entity
@NamedQuery(name="Login.findAll", query="SELECT l FROM Login l")
public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="login_id")
	private int loginId;

	@Column(name="login_name")
	private String loginName;

	@Column(name="login_password")
	private String loginPassword;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_regist_date")
	private Date loginRegistDate;
	
	@Column(name="status")
	private int status ;

	public Login() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLoginId() {
		return this.loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public Date getLoginRegistDate() {
		return this.loginRegistDate;
	}

	public void setLoginRegistDate(Date loginRegistDate) {
		this.loginRegistDate = loginRegistDate;
	}

}