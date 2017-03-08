package com.join.web.controller;

import com.join.web.model.JoinModel;
import com.join.web.encrypt.Rsa;
import com.join.web.hash.HashPwd;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import javax.servlet.http.HttpServletRequest;


@Controller
public class joinus {

    public KeyPair pair = Rsa.generateKeyPair();
    public PublicKey pubkey = pair.getPublic();
    public PrivateKey privkey = pair.getPrivate();

    @RequestMapping("/")
    public String join(){
        return "simplecaptcha";
    }

    @RequestMapping("/captchaSubmit")
    public String captchaSubmit(){
        return "captchaSubmit";
    }


    @RequestMapping("/email")
    public String emailsent(JoinModel model, Model M) throws Exception{
        String email = model.getEmail();
        String passwd = model.getPasswd();
        String phone = model.getPhone();
        String addr = model.getAddr();
        String daddr = model.getDaddr();
        String filename;

        passwd = HashPwd.getSaltedHash(passwd);

        String enc = Rsa.encrypt(email, pubkey);
        String url = "https://211.249.63.75/join/emailverification?code="+enc;

        M.addAttribute("url", url);
        
        File rfile = new File("nfile" + ".txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(rfile));
            filename = br.readLine();

        } catch(FileNotFoundException e){
            return "Error";
        } catch(IOException e) {
            return "Error";
        }

        File file = new File(email+".txt");
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter(file));
            out.write(email); out.newLine();
            out.write(passwd); out.newLine();
            out.write(phone); out.newLine();
            out.write(addr); out.newLine();
            out.write(daddr); out.newLine();
            out.write(filename); out.newLine();
            out.flush();
            out.close();
        } catch(IOException e){
            return "simplecaptcha";
        }

            return "email";
    }

    @RequestMapping("/fileupload")
    public String jofileupload(){
        return "fileupload";
    }

    @RequestMapping("/upload.action")
    public ModelAndView upolad(
            @RequestParam("fileinput") MultipartFile file){

        ModelAndView mav = new ModelAndView();

        String filename = file.getOriginalFilename();

        int i = filename.indexOf(".");
        String Exten = filename.substring(i);
        filename =RandomStringUtils.randomAlphabetic(10);


 
        File f = new File(filename + Exten);

        if(f.exists()){
            filename = filename + "(1)" + Exten;
            f = new File(filename);
        }
        else{
        	filename = filename + Exten;
        }
        
        File rfile = new File("nfile"+".txt");
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter(rfile));
            out.write(filename); out.newLine();
            out.flush();
            out.close();
        } catch(IOException e){
            return null;
        }

        try {
            file.transferTo(f);
        } catch (IOException e) {
            return null;
        }

        return mav;
    }


    @RequestMapping("/emailverification")
    public String emailverification(@RequestParam(required=false) String code, Model M, JoinModel model, HttpServletRequest request){
        String root_path = request.getSession().getServletContext().getRealPath("/");

        M.addAttribute("root_path", root_path);

        String decode = Rsa.decrypt(code, privkey);

        M.addAttribute("decode", decode);

        File file = new File(decode + ".txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String email = br.readLine();
            String passwd = br.readLine();
            String phone = br.readLine();
            String addr = br.readLine();
            String daddr = br.readLine();
            String filename = br.readLine();

            M.addAttribute("email", email);
            M.addAttribute("phone", phone);
            M.addAttribute("addr", addr);
            M.addAttribute("detailedaddr", daddr);
            M.addAttribute("filename", filename);

            return "welcome";

        } catch(FileNotFoundException e){
            return "mailverificationError";
        } catch(IOException e) {
            return "mailverificationError";
        }

    }
}

