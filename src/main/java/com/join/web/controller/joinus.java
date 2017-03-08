package com.join.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import com.join.web.model.JoinModel;
import com.join.web.encrypt.Rsa;
import java.io.*;


import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;



import java.util.Random;
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

    @RequestMapping("/captchaSubmit")
    public String captchaSubmit(){
        return "captchaSubmit";
    }

    @RequestMapping("/email")
    public String emailsent(JoinModel model, Model M){
        String email = model.getEmail();
        String passwd = model.getPasswd();
        String phone = model.getPhone();
        String addr = model.getAddr();
        String daddr = model.getDaddr();



        String enc = Rsa.encrypt(email, pubkey);
        String url = "https://211.249.63.75/join/emailverification?code="+enc;

        M.addAttribute("code", enc);

        File file = new File(email+".txt");
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter(file));
            out.write(email); out.newLine();
            out.write(passwd); out.newLine();
            out.write(phone); out.newLine();
            out.write(addr); out.newLine();
            out.write(daddr); out.newLine();
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

        File f = new File(filename);

        if(f.exists()){
            filename = filename + "(1)";
            f = new File(filename);
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

            M.addAttribute("email", email);
            M.addAttribute("phone", phone);
            M.addAttribute("addr", addr);
            M.addAttribute("detailedaddr", daddr);

            return "welcome";

        } catch(FileNotFoundException e){
            return "mailverificationError";
        } catch(IOException e) {
            return "mailverificationError";
        }

    }
}

