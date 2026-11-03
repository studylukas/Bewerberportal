package com.example.demo.recruiter;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Stellen.Stellen;
import com.example.demo.bewerbung.Bewerbung;
import com.example.demo.bewerbung.BewerbungService;
import com.example.demo.bewerbung.BewerbungStatus;

@Controller 
@RequestMapping("/recruiter")
public class RecruiterController {
    private final RecruiterService recruiterService;
    private final BewerbungService bewerbungService;
    
    public RecruiterController(RecruiterService recruiterService, BewerbungService bewerbungService){
        this.recruiterService = recruiterService;
        this.bewerbungService = bewerbungService;
    }
    
    @PostMapping("/stellen")
    public ResponseEntity<Stellen> neueStelleAnlegen(@RequestBody Stellen neueStelle) {
        // Ruft deine bereits existierende Methode im Service auf
        Stellen gespeicherteStelle = recruiterService.stelleAnlegen(neueStelle);
        return ResponseEntity.ok(gespeicherteStelle);
    }
    
    @GetMapping("/listeBewerbungen")
    public String bewerbungenAnzeigen(Model model){
        List<Bewerbung> alleBewerbungen = bewerbungService.alleBewerbungenAnzeigen();
        
        model.addAttribute("bewerbungListe", alleBewerbungen);
        
        return "Recruiter/listeBewerbungen";
    }
    
    @GetMapping("/bewerbungen/{bewerberId}/{stellenId}")
    public String bewerbungDetails(@PathVariable Long bewerberId, @PathVariable Long stellenId, Model model){
        model.addAttribute("bewerbung", bewerbungService.bewerbungFinden(bewerberId, stellenId));
        model.addAttribute("statusWerte", BewerbungStatus.values());
        model.addAttribute("verlauf", bewerbungService.statusHistorie(bewerberId, stellenId));
        return "Recruiter/bewerbungDetails";
    }
    
    @PostMapping("/bewerbungen/{bewerberId}/{stellenId}/status")
    public String statusAendern(@PathVariable Long bewerberId,
                            @PathVariable Long stellenId,
                            @RequestParam BewerbungStatus status, Principal principal) {
        Recruiter aktuellerBearbeiter = recruiterService.bearbeiter(principal.getName());                        
        bewerbungService.statusAendern(bewerberId, stellenId, status, aktuellerBearbeiter);
    
        return "redirect:/recruiter/bewerbungen/" + bewerberId + "/" + stellenId;
}

}
