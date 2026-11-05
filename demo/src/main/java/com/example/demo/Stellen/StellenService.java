package com.example.demo.Stellen;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NichtGefundenException;

@Service 
public class StellenService {

    private final StellenRepository stellenRepository;
    
    public StellenService(StellenRepository stellenRepository){
        this.stellenRepository = stellenRepository;
    }
    
    public List<Stellen> alleStellenAnzeigen(){
        return stellenRepository.findAll();
    }

    public Stellen stelleSuchenId(Long id) {
        return stellenRepository.findById(id)
                .orElseThrow(() -> new NichtGefundenException("Diese Stelle gibt es nicht."));
    }

    // Für Bewerber: nur offene Stellen sind abrufbar; geschlossene verhalten sich wie nicht vorhanden
    public Stellen offeneStelleFinden(Long id) {
        return stellenRepository.findById(id)
                .filter(s -> s.getStellenStatus() == StellenStatus.OFFEN)
                .orElseThrow(() -> new NichtGefundenException("Diese Stelle ist nicht mehr ausgeschrieben."));
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
