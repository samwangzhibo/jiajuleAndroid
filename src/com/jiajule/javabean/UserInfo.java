package com.jiajule.javabean;

public class UserInfo {
	public String username;
	String userpass;
	public String password;
	public UserInfo() {
		super();
	}
	public UserInfo(String username,String userpass){
		this.username=username;
		this.userpass=userpass;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
		
}
