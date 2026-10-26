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
    
    public Bewerber BewerberAnlegen(Bewerber bewerber){
        
       if(!bewerber.getEmail().contains("@")) {
            throw new RuntimeException("Ungültige E-Mail");
        }
        
        return bewerberRepository.save(bewerber);
    }
    
        public List<Stellen> alleStellenAnzeigen(){
        return stellenRepository.findAll();
    }
}
