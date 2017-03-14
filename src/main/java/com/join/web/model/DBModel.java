package com.join.web.model;

public class DBModel {

	private String email;
	private String passwd;
	private String phone;
	private String addr;
	private String img;


	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getPasswd(){
		return passwd;
	}
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getAddr(){
		return addr;
	}
	public void setAddr(String addr){
		this.addr = addr;
	}

    public String getImg(){
		return img;
	}
	public void setImg(String img){
		this.img = img;
	}
}
