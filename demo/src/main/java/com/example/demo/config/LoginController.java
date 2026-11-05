package com.example.demo.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class LoginController {

    @GetMapping ("/login")
    public String login(){return "auth/login";}
    
    @GetMapping("/startseite")
    public String showStartseite(){
        return "startseite";
    }
}
