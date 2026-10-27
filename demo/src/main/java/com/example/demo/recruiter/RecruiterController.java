package com.example.demo.recruiter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Stellen.Stellen;

@Controller 
@RequestMapping("/Recruiter")
public class RecruiterController {
    private final RecruiterService recruiterService;
    
    public RecruiterController(RecruiterService recruiterService){
        this.recruiterService = recruiterService;
    }
    
    
}
