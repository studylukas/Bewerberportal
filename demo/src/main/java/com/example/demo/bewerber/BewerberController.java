package com.example.demo.bewerber;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.bewerbung.Bewerbung;
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
    public String startseite(@RequestParam(required = false) String stichwort, @RequestParam(required = false) String bereich, @RequestParam(required = false) String standort, Model model){
        model.addAttribute("bewerberListe", bewerberService.stellenSuchen(stichwort, bereich, standort ));
        model.addAttribute("bereiche", bewerberService.alleBereiche());
        model.addAttribute("standorte", bewerberService.alleStandorte());
        model.addAttribute("stichwort", stichwort);
        model.addAttribute("bereich", bereich);
        model.addAttribute("standort", standort);
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
                                  @RequestParam (name = "gehaltswunsch", required = false) BigDecimal gehaltswunsch,
                                  @RequestParam (name = "unterlagen") List<MultipartFile> unterlagen) throws IOException {         
        if (!einwilligung){
            throw new IllegalArgumentException("Ohne Einwilligung kann die Bewerbung nicht gespeichert werden.");
        }
        // Ohne Stelle gibt es keine Bewerbung -> nichts speichern
        if (stellenId == null) {
            return "redirect:/";
        }

        // Bewerber + Bewerbung werden gemeinsam in einer Transaktion gespeichert
        Bewerbung bewerbung = bewerbungService.bewerben(bewerber, stellenId, anschreiben, gehaltswunsch, unterlagen);

        return "redirect:/bewerbung/status/" + bewerbung.getZugangToken();
    }

    @GetMapping("/bewerbung/status/{token}")
    public String zeigeAktuellenStatus(@PathVariable("token") String token, Model model){
        model.addAttribute("bewerbung", bewerbungService.bewerbungFindenPerToken(token));
        // Vollständiger Link (inkl. http://host:port), damit der Bewerber ihn speichern kann
        model.addAttribute("statusLink", ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
        return "Bewerber/status";
    }

}

