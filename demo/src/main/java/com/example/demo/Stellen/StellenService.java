package com.example.demo.Stellen;

import java.util.List;
import org.springframework.stereotype.Service;

@Service 
public class StellenService {

    private final StellenRepository stellenRepository;
    
    public StellenService(StellenRepository stellenRepository){
        this.stellenRepository = stellenRepository;
    }
    
    public Stellen stelleAnlegen(Stellen stellen){
        return stellenRepository.save(stellen);
    }
    
    public List<Stellen> alleStellenAnzeigen(){
        return stellenRepository.findAll();
    }
    
    public Stellen stelleSuchenId(Long id) {
    return stellenRepository.findById(id).orElse(null);
}

}
