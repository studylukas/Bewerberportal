package com.example.demo.bewerbung;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Stellen.Stellen;
import com.example.demo.Stellen.StellenRepository;
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

    public BewerbungService(BewerbungRepository bewerbungRepository, BewerberRepository bewerberRepository, StellenRepository stellenRepository, StatusAenderungRepository statusAenderungRepository){
        this.bewerbungRepository = bewerbungRepository;
        this.bewerberRepository = bewerberRepository;
        this.stelleRepository = stellenRepository;
        this.statusAenderungRepository = statusAenderungRepository;
    }

    @Transactional
    public Bewerbung bewerben(Long bewerberId, Long stelleId, String anschreiben, BigDecimal gehaltswunsch) {
        if (bewerbungRepository.existsById(new BewerbungId(bewerberId, stelleId))) {
            throw new IllegalStateException("Bereits auf diese Stelle beworben");
        }
        Bewerber b = bewerberRepository.findById(bewerberId).orElseThrow();
        Stellen s = stelleRepository.findById(stelleId).orElseThrow();
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
}