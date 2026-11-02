package com.example.demo.bewerber;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.bewerbung.BewerbungService;

@Controller 
public class BewerberController {
    private final BewerberService bewerberService;
    private final BewerbungService bewerbungService;

    public BewerberController(BewerberService bewerberService, BewerbungService bewerbungService){
        this.bewerberService = bewerberService;
        this.bewerbungService = bewerbungService;
    }

    @GetMapping("/")
    public String startseite(Model model){
        model.addAttribute("bewerberListe", bewerberService.alleStellenAnzeigen());
        return "Bewerber/liste";
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
                                  @RequestParam(name = "anschreiben", required = false) String anschreiben,
                                  @RequestParam (name = "einwilligung", defaultValue = "false") boolean einwilligung,
                                  @RequestParam (name = "gehaltswunsch", required = false) BigDecimal gehaltswunsch){
        
        if (!einwilligung){
            throw new IllegalArgumentException("Ohne Einwilligung kann die Bewerbung nicht gespeichert werden.");
        }
        Bewerber gespeichert = bewerberService.BewerberAnlegen(bewerber);

        if (stellenId != null) {
            bewerbungService.bewerben(gespeichert.getId(), stellenId, anschreiben, gehaltswunsch);
        }
        
        return "redirect:/";
    }

}

