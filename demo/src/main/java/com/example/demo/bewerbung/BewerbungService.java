package com.example.demo.bewerbung;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Stellen.Stellen;
import com.example.demo.Stellen.StellenRepository;
import com.example.demo.Stellen.StellenStatus;
import com.example.demo.bewerber.Bewerber;
import com.example.demo.bewerber.BewerberRepository;
import com.example.demo.exception.NichtGefundenException;
import com.example.demo.recruiter.Recruiter;

import jakarta.transaction.Transactional;

@Service
public class BewerbungService {

    private final BewerbungRepository bewerbungRepository;
    private final BewerberRepository bewerberRepository;
    private final StellenRepository stelleRepository;
    private final StatusAenderungRepository statusAenderungRepository;
    private final NotizRepository notizRepository;

    public BewerbungService(BewerbungRepository bewerbungRepository, BewerberRepository bewerberRepository, StellenRepository stellenRepository, StatusAenderungRepository statusAenderungRepository, NotizRepository notizRepository){
        this.bewerbungRepository = bewerbungRepository;
        this.bewerberRepository = bewerberRepository;
        this.stelleRepository = stellenRepository;
        this.statusAenderungRepository = statusAenderungRepository;
        this.notizRepository = notizRepository;
    }

    @Transactional
    public Bewerbung bewerben(Long bewerberId, Long stelleId, String anschreiben, BigDecimal gehaltswunsch) {
        if (bewerbungRepository.existsById(new BewerbungId(bewerberId, stelleId))) {
            throw new IllegalStateException("Bereits auf diese Stelle beworben");
        }
        Bewerber b = bewerberRepository.findById(bewerberId).orElseThrow();
        Stellen s = stelleRepository.findById(stelleId).orElseThrow();
        // Auf geschlossene Stellen darf man sich nicht bewerben (z. B. über eine alte URL)
        if (s.getStellenStatus() != StellenStatus.OFFEN) {
            throw new NichtGefundenException("Diese Stelle ist nicht mehr ausgeschrieben.");
        }
        Bewerbung gespeichert = bewerbungRepository.save(new Bewerbung(b, s, anschreiben, gehaltswunsch));
        statusAenderungRepository.save(new StatusAenderung(bewerberId, stelleId,null, BewerbungStatus.EINGEGANGEN, null));
        return gespeichert;
    }

    @Transactional
    public void statusAendern(Long bewerberId, Long stelleId, BewerbungStatus neu, Recruiter recruiter) {
        Bewerbung bw = bewerbungRepository.findById(new BewerbungId(bewerberId, stelleId))
                .orElseThrow();
        BewerbungStatus statusAlt = bw.getStatus();
        if(statusAlt == neu){
            return;
        }
        statusAenderungRepository.save(new StatusAenderung(bewerberId, stelleId, statusAlt, neu, recruiter));
        bw.setStatus(neu);   
    }
    
    public List<Bewerbung> alleBewerbungenAnzeigen(){
        return bewerbungRepository.findAll();
    }
    
    public Bewerbung bewerbungFinden(Long bewerberId, Long stellenId){
        return bewerbungRepository.findById(new BewerbungId(bewerberId, stellenId)).orElseThrow();
    }

    public Bewerbung bewerbungFindenPerToken(String zugangToken){
        return bewerbungRepository.findByZugangToken(zugangToken)
                .orElseThrow(() -> new NichtGefundenException(
                        "Zu diesem Link wurde keine Bewerbung gefunden. Bitte prüfen Sie, ob der Link vollständig ist."));
    }
    
    public List<StatusAenderung> statusHistorie(Long bewerberId, Long stellenId){
        return statusAenderungRepository.findByBewerberIdAndStellenIdOrderByGeaendertAmAsc(bewerberId, stellenId);
    }
    
    @Transactional
    public void bewertungAendern(Long bewerberId, Long stellenId, Integer bewertung){
        if( bewertung == null || bewertung > 5 || bewertung < 1) {throw new IllegalArgumentException("Die Bewertung muss zwischen 1 und 5 erfolgen."); }
        
        Bewerbung b = bewerbungRepository.findById(new BewerbungId(bewerberId, stellenId)).orElseThrow();
        b.setBewertung(bewertung);
    }
    
    @Transactional 
    public void notizHinzufuegen(Long bewerberId, Long stellenId, String text, Recruiter recruiter){
        if(text == null || text.isEmpty()) {return;}
        
        notizRepository.save(new Notiz(bewerberId, stellenId, text, recruiter));
    }
    
    public List<Notiz> notizFinden(Long bewerberId, Long stellenId){
        return notizRepository.findByBewerberIdAndStellenIdOrderByErstelltAmDesc(bewerberId, stellenId);
    }
    
}

