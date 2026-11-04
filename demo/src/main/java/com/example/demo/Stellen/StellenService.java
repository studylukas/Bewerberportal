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

    public Stellen stelleSpeichern(Stellen formular){
        if(formular.getId() == null) {
            return stellenRepository.save(formular);
        }
        Stellen bestehend = stellenRepository.findById(formular.getId()).orElseThrow();
        bestehend.setBezeichnung(formular.getBezeichnung());
        bestehend.setBeschreibung(formular.getBeschreibung());
        bestehend.setBereich(formular.getBereich());
        bestehend.setStellenStatus(formular.getStellenStatus());
        bestehend.setStandort(formular.getStandort());
        return stellenRepository.save(bestehend);
    }
       

}
