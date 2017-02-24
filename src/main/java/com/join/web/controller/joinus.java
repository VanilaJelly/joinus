package com.join.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.join.web.model.RequestModel;

@Controller
public class joinus {
	
	public String email;
	public String phone;
	public String passwd;
	

	@RequestMapping("/")
	public String join(){
		return "joinformat";
	}
	
	@RequestMapping("/welcome")
	public String welcome(RequestModel model, Model M){
		
		email = model.getEmail();
		passwd = model.getPasswd();
		phone = model.getPhone();
		
		M.addAttribute("email", email);
		M.addAttribute("phone", phone);
		
		return "welcome";
	}
}
