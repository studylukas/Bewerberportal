package com.example.demo.bewerber;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Stellen.Stellen;
import com.example.demo.Stellen.StellenRepository;

@Service 
public class BewerberService {
    private final BewerberRepository bewerberRepository;
    private final StellenRepository stellenRepository;
    
    public BewerberService(BewerberRepository bewerberRepository, StellenRepository stellenRepository){
        this.bewerberRepository = bewerberRepository;
        this.stellenRepository = stellenRepository;
    }
    
    public List<Stellen> alleStellenAnzeigen(){
        return stellenRepository.findAll();
    }

    public List<Stellen> stellenSuchen(String stichwort, String bereich, String standort){
        return stellenRepository.suchen(leerZuNull(stichwort), leerZuNull(bereich), leerZuNull(standort));
    }

    public List<String> alleBereiche(){
        return stellenRepository.alleBereiche();
    }

    public List<String> alleStandorte(){
        return stellenRepository.alleStandorte();
    }

    // Leere Formularfelder kommen als "" an; die Suche erwartet in dem Fall null (= Filter aus)
    private String leerZuNull(String wert){
        if (wert == null || wert.isBlank()) {
            return null;
        }
        return wert.trim();
    }
    
    public List<Bewerber> alleBewerberAnzeigen(){
        return bewerberRepository.findAll();
    }
}
