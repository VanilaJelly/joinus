package com.join.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.ui.Model;
import com.join.web.model.JoinModel;
import com.join.web.encrypt.Rsa;
import java.io.*;


import java.security.Key; 
import java.security.KeyPair;







import javax.servlet.http.HttpServletRequest;

@Controller
public class joinus {
    
    public String email;
    public String phone;
    public String passwd;
    
    public KeyPair pair = Rsa.generateKeyPair();
    public Key pubkey = pair.getPublic();
    public Key privkey = pair.getPrivate();
    public String code;

    @RequestMapping("/")
    public String join(){
        return "simplecaptcha";
    }
    
    @RequestMapping("/welcome")
    public String welcome(JoinModel model, Model M){
        
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

    @RequestMapping("/captchaSubmit")
    public String captchaSubmit(){
        return "captchaSubmit";
    }

    @RequestMapping("/email")
    public String emailreg(JoinModel model, HttpServletRequest request, Model M){
        email = model.getEmail();
        passwd = model.getPasswd();
        phone = model.getPhone();
        
        code = Rsa.encrypt(email, pubkey);
        String url = "https://211.249.63.75/join/emailverification?code="+code;
        
        M.addAttribute("code", code);

        File file = new File("C:\\a.txt");
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter(file));
            out.write(email); out.newLine();
            out.write(passwd); out.newLine();
            out.write(phone); out.newLine();
            out.write(url); out.newLine();
            out.flush();
            out.close();
            return "email";
        } catch(IOException e){
            return "simplecaptcha";
        }

    }

    @RequestMapping("/emailverification")
    public String emailverification(@RequestParam(required=false) String num, Model M, HttpServletRequest request){
        String root_path = request.getSession().getServletContext().getRealPath("/");

        M.addAttribute("root_path", root_path);
        
        String ex = "uXGWkgK9eMB0O1fde/3GTnw0s9B0aA2hEHinz567MwHCZu2k5T90LRqOIG9vjDwhYFxpksEsYgzy7hZvA3L/epiaGUzT2S1l/A6jvmBDlLCV2cHRXOt71ALuQklt6Rd20ZVnWRNrJpvWBePZAABvJMd0rbCvh+IxPZjLVL2rX58=";
        String decode = Rsa.decrypt(ex, privkey);
       
        M.addAttribute("decode", decode);

        File file = new File("C:\\a.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            email = br.readLine();
            passwd = br.readLine();
            phone = br.readLine();

            M.addAttribute("email", email);
            M.addAttribute("phone", phone);
        } catch(FileNotFoundException e){
            return "welcome";
        } catch(IOException e) {
            return "welcome";
        }

        return "welcome";
    }
}

