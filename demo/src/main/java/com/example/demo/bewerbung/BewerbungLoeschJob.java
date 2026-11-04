package com.example.demo.bewerbung;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.bewerber.BewerberRepository;

import jakarta.transaction.Transactional;

@Component 
public class BewerbungLoeschJob {
    private final StatusAenderungRepository statusAenderungRepository;
    private final BewerbungRepository bewerbungRepository;
    private final BewerberRepository bewerberRepository;
    
    public BewerbungLoeschJob(StatusAenderungRepository statusAenderungRepository, BewerbungRepository bewerbungRepository, BewerberRepository bewerberRepository){
        this.statusAenderungRepository = statusAenderungRepository;
        this.bewerbungRepository = bewerbungRepository;
        this.bewerberRepository = bewerberRepository;        
    }
    
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional 
    public void loeschen(){
        LocalDateTime stichtag = LocalDateTime.now().minusMonths(6);
        List<StatusAenderung> abgelehnteBewerbungen = statusAenderungRepository.findByNachStatusAndGeaendertAmBefore(BewerbungStatus.ABGELEHNT, stichtag);
        int protokoll = 0;
        for(StatusAenderung eintrag : abgelehnteBewerbungen){
            List<StatusAenderung> verlauf = statusAenderungRepository.findByBewerberIdAndStellenIdOrderByGeaendertAmAsc(eintrag.getBewerberId(), eintrag.getStellenId());
            
            if (verlauf.isEmpty()){
                continue;
            }
            StatusAenderung letzter = verlauf.get(verlauf.size() - 1);
            if (letzter.getNachStatus() != BewerbungStatus.ABGELEHNT || !letzter.getGeaendertAm().isBefore(stichtag)){
                continue;
            }
            
            bewerbungRepository.deleteById(new BewerbungId(eintrag.getBewerberId(), eintrag.getStellenId()));
            protokoll++;
            
            if (bewerbungRepository.findByIdBewerberId(eintrag.getBewerberId()).isEmpty()){
                bewerberRepository.deleteById(eintrag.getBewerberId());
            }
        }
        System.out.println("Es wurden " + protokoll + " Bewerbungen gelöscht!");
    }
}
