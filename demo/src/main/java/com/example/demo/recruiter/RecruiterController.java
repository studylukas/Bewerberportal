package com.example.demo.recruiter;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Stellen.Stellen;

@Controller 
@RequestMapping("/Recruiter")
public class RecruiterController {
    private final RecruiterService recruiterService;
    
    public RecruiterController(RecruiterService recruiterService){
        this.recruiterService = recruiterService;
    }
    
        @PostMapping("/stellen")
    public ResponseEntity<Stellen> neueStelleAnlegen(@RequestBody Stellen neueStelle) {
        // Ruft deine bereits existierende Methode im Service auf
        Stellen gespeicherteStelle = recruiterService.stelleAnlegen(neueStelle);
        return ResponseEntity.ok(gespeicherteStelle);
    }
}
