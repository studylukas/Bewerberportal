package com.example.demo.bewerber;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Stellen.Stellen;

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
    
    @GetMapping("bewerber/liste")
    public String listeAnzeige(Model model){
        List<Stellen> alleStellen = bewerberService.alleStellenAnzeigen();
        
        model.addAttribute("bewerberListe", alleStellen);
        
        return "Bewerber/liste";
    }
    
}


