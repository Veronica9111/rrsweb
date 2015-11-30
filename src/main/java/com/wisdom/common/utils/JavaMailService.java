package com.wisdom.common.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.common.utils.JavaMail;

@Service("javaMailService")
public class JavaMailService {
	private JavaMail javaMail;
	private String username;
	private String password;
	
	public void JavaMail(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public boolean sendMailOut(String to,String subject,String body,String from_user){
		JavaMail javaMail = new JavaMail("smtp.bangbangzhang.com");
		javaMail.setNamePass("bbz@bangbangzhang.com", "bhu89IJN");
		javaMail.setNeedAuth(true);
		javaMail.setFrom("bbz@bangbangzhang.com");
		javaMail.setTo(to);
		javaMail.setSubject(subject);
		javaMail.setBody(body);
		StringBuffer strRet = new StringBuffer();
		boolean blRet = javaMail.sendout(strRet);
		return blRet;
	}
//	
//	public static final void main(String[] args){
//		JavaMailService mail = new JavaMailService();
//		
//	}

	public JavaMail getJavaMail() {
		return javaMail;
	}

	public void setJavaMail(JavaMail javaMail) {
		this.javaMail = javaMail;
	}

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
