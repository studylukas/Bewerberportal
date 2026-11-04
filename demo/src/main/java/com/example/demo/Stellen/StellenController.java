package com.example.demo.Stellen;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class StellenController {
    private final StellenService stellenService;
    
    public StellenController(StellenService stellenService){
        this.stellenService = stellenService;
    }
    
   @GetMapping("/stellen")
    public String zeigeFormular(Model model){
        model.addAttribute("stellen", new Stellen());
        model.addAttribute("statusWerte", StellenStatus.values());
        
        return "Stellen/stellen";
    }
    
    @PostMapping("/stellen")
    public String stelleAnlegen(@ModelAttribute Stellen stellen){
        stellenService.stelleSpeichern(stellen);
        
        return "redirect:/stellen/liste";
    }
    
    @GetMapping("/stellen/liste")
    public String listeAnzeige(Model model){
        List<Stellen> alleStellen = stellenService.alleStellenAnzeigen();
        
        model.addAttribute("stellenListe", alleStellen);
        
        return "Stellen/liste";
    }
    
    @GetMapping("/stellen/bearbeiten/{id}")
    public String zeigeBearbeitenFormular(@PathVariable("id") Long id, Model model) {
        Stellen bestehendeStelle = stellenService.stelleSuchenId(id);
        // Wir übergeben die bestehende Stelle an dasselbe Formular wie beim Anlegen
        model.addAttribute("stellen", bestehendeStelle);
        model.addAttribute("statusWerte", StellenStatus.values()); 
        return "Stellen/stellen"; 
    }

    @GetMapping("/bewerber/stellen/{id}")
    public String zeigeDetailsBewerber(@PathVariable("id") Long id, Model model) {
        Stellen gefundeneStelle = stellenService.offeneStelleFinden(id);
        model.addAttribute("stelle", gefundeneStelle);
        
        // Verweist auf templates/Bewerber/details.html
        return "Bewerber/details"; 
    }

    @GetMapping("/stellen/details/{id}")
    public String zeigeDetails(@PathVariable("id") Long id, Model model) {
        Stellen gefundeneStelle = stellenService.stelleSuchenId(id); 
        model.addAttribute("stelle", gefundeneStelle);
        
        // Verweist auf templates/Stellen/details.html
        return "Stellen/details"; 
    }
}
