package com.example.demo.bewerber;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Stellen.Stellen;
import com.example.demo.bewerbung.BewerbungService;

@Controller 
public class BewerberController {
    private final BewerberService bewerberService;
    private final BewerbungService bewerbungService;

    public BewerberController(BewerberService bewerberService, BewerbungService bewerbungService){
        this.bewerberService = bewerberService;
        this.bewerbungService = bewerbungService;
    }

    @GetMapping ("/")
    public String startseite(){
        return "index";
    }
    
    @GetMapping ("/bewerber")
    public String zeigeFormular(@RequestParam(name = "stellenId", required = false) Long stellenId, Model model){

        model.addAttribute("bewerber", new Bewerber());
        model.addAttribute("stellenId", stellenId);

        return "Bewerber/bewerber";
    }

    @PostMapping("/bewerber")
    public String bewerberAnlegen(@ModelAttribute Bewerber bewerber,
                                  @RequestParam(name = "stellenId", required = false) Long stellenId,
                                  @RequestParam(name = "anschreiben", required = false) String anschreiben){
        Bewerber gespeichert = bewerberService.BewerberAnlegen(bewerber);

        if (stellenId != null) {
            bewerbungService.bewerben(gespeichert.getId(), stellenId, anschreiben);
        }

        return "redirect:/bewerber/liste";
    }
    
    @GetMapping("bewerber/liste")
    public String listeAnzeige(Model model){
        List<Stellen> alleStellen = bewerberService.alleStellenAnzeigen();
        
        model.addAttribute("bewerberListe", alleStellen);
        
        return "Bewerber/liste";
    }
    
}

