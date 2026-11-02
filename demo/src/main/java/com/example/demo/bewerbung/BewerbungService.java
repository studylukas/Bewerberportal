package com.example.demo.bewerbung;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Stellen.Stellen;
import com.example.demo.Stellen.StellenRepository;
import com.example.demo.bewerber.Bewerber;
import com.example.demo.bewerber.BewerberRepository;

import jakarta.transaction.Transactional;

@Service
public class BewerbungService {

    private final BewerbungRepository bewerbungRepository;
    private final BewerberRepository bewerberRepository;
    private final StellenRepository stelleRepository;

    public BewerbungService(BewerbungRepository bewerbungRepository, BewerberRepository bewerberRepository, StellenRepository stellenRepository){
        this.bewerbungRepository = bewerbungRepository;
        this.bewerberRepository = bewerberRepository;
        this.stelleRepository = stellenRepository;
    }

    @Transactional
    public Bewerbung bewerben(Long bewerberId, Long stelleId, String anschreiben) {
        if (bewerbungRepository.existsById(new BewerbungId(bewerberId, stelleId))) {
            throw new IllegalStateException("Bereits auf diese Stelle beworben");
        }
        Bewerber b = bewerberRepository.findById(bewerberId).orElseThrow();
        Stellen s = stelleRepository.findById(stelleId).orElseThrow();
        return bewerbungRepository.save(new Bewerbung(b, s, anschreiben));
    }

    @Transactional
    public void statusAendern(Long bewerberId, Long stelleId, BewerbungStatus neu) {
        Bewerbung bw = bewerbungRepository.findById(new BewerbungId(bewerberId, stelleId))
                .orElseThrow();
        bw.setStatus(neu);   // wird beim Commit automatisch gespeichert
    }
    
    public List<Bewerbung> alleBewerbungenAnzeigen(){
        return bewerbungRepository.findAll();
    }
    
    public Bewerbung bewerbungFinden(Long bewerberId, Long stellenId){
        return bewerbungRepository.findById(new BewerbungId(bewerberId, stellenId)).orElseThrow();
    }
}