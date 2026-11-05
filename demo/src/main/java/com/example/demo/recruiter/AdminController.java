package com.example.demo.recruiter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping ("/admin")
public class AdminController {
    private final RecruiterService recruiterService;
    
    
    public AdminController(RecruiterService recruiterService){
        this.recruiterService = recruiterService;
        
    }
    
    @GetMapping ("/recruiter")
    public String alleRecruiter(Model model){
        model.addAttribute("recruiterListe", recruiterService.alleRecruiter());
        model.addAttribute("rollen", Rolle.values());        
        return "admin/recruiter";
    }
    
    @PostMapping ("/recruiter")
    public String recruiterAnlegen(@RequestParam String name, 
                                   @RequestParam String email, 
                                   @RequestParam String passwort,
                                   @RequestParam Rolle rolle){
        recruiterService.registerRecruiter(email, passwort, name, rolle);
        return "redirect:/admin/recruiter";
    }
    
}
