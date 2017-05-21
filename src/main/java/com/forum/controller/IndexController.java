package com.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.util.PasswordUtil;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index() {
		return "/index" ;
	}
	
	@RequestMapping("/admin")
	public String gotoUserLogin() {
		return "/admin/index" ;
	}
	
	@RequestMapping("/passwordTransformer")
	@ResponseBody
	public String passwordTransformer(String password) {
		password = PasswordUtil.GenHashBySHA256(password, PasswordUtil._salt) ;
		
		try {
			password = PasswordUtil.aesEncrypt(password, PasswordUtil._key) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return password ;
	}
	
	@RequestMapping("/gotoRegister")
	public String gotoRegister() {
		return "/register" ;
	}
}
