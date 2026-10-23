package com.example.demo.bewerber;

import org.springframework.stereotype.Service;

@Service 
public class BewerberService {
    private final BewerberRepository bewerberRepository;
    
    public BewerberService(BewerberRepository bewerberRepository){
        this.bewerberRepository = bewerberRepository;
    }
    
    public Bewerber BewerberAnlegen(Bewerber bewerber){
        
       if(!bewerber.getEmail().contains("@")) {
            throw new RuntimeException("Ungültige E-Mail");
        }
        
        return bewerberRepository.save(bewerber);
    }
}
