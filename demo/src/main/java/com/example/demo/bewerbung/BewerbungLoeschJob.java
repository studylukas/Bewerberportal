package com.example.demo.bewerbung;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component 
public class BewerbungLoeschJob {
    private final StatusAenderungRepository statusAenderungRepository;
    
    public BewerbungLoeschJob(StatusAenderungRepository statusAenderungRepository){
        this.statusAenderungRepository = statusAenderungRepository;        
    }
    
    public void loeschen(){
        LocalDateTime stichtag = LocalDateTime.now().minusMonths(6);
        List<StatusAenderung> abgelehnteBewerbungen = statusAenderungRepository.findByNachStatusAndGeaendertAmBefore(BewerbungStatus.ABGELEHNT, stichtag);
        int protokoll = 0;
        for(StatusAenderung eintrag : )
    }
}
