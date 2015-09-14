package com.unieap.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator  extends Authenticator{
	 private String username, password;
	 public MyAuthenticator(String username, String password) {
	    this.username = username;
	    this.password = password;
	 }
	 protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(username, password);
	 }
}
