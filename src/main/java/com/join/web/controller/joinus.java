package com.join.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	
	 @RequestMapping(method=RequestMethod.POST)
	 public String handleFile(MultipartHttpServletRequest request)
	 {
	  MultipartFile file = request.getFile("filedata");
	  //some code here
	  return "fileupload/success";
	 }

}
