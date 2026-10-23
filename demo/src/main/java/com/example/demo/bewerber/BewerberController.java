package com.example.demo.bewerber;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class BewerberController {
    private final BewerberService bewerberService;
    
    public BewerberController(BewerberService bewerberService){
        this.bewerberService = bewerberService;
    }

    @GetMapping ("/")
    public String startseite(){
        return "index";
    }
    
    @GetMapping ("/bewerber")
    public String zeigeFormular(Model model){
        
        model.addAttribute("bewerber", new Bewerber());
        
        return "Bewerber/bewerber";
    }
    
    @PostMapping("/bewerber")
    public String bewerberAnlegen(@ModelAttribute Bewerber bewerber){
        bewerberService.BewerberAnlegen(bewerber);
        
        return "redirect:/";
    }
    
}


