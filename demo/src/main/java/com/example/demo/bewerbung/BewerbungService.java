package com.example.demo.bewerbung;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final DokumentRepository dokumentRepository;

    public BewerbungService(BewerbungRepository bewerbungRepository, BewerberRepository bewerberRepository, StellenRepository stellenRepository, StatusAenderungRepository statusAenderungRepository, NotizRepository notizRepository, DokumentRepository dokumentRepository){
        this.bewerbungRepository = bewerbungRepository;
        this.bewerberRepository = bewerberRepository;
        this.stelleRepository = stellenRepository;
        this.statusAenderungRepository = statusAenderungRepository;
        this.notizRepository = notizRepository;
        this.dokumentRepository = dokumentRepository;
    }

    // Bewerber und Bewerbung werden in EINER Transaktion gespeichert: schlägt ein Schritt fehl,
    // wird alles zurückgerollt und es bleiben keine personenbezogenen Daten ohne Bewerbung zurück.
    @Transactional
    public Bewerbung bewerben(Bewerber bewerber, Long stelleId, String anschreiben, BigDecimal gehaltswunsch, List<MultipartFile> unterlagen) throws IOException {
        if (bewerber.getEmail() == null || !bewerber.getEmail().contains("@")) {
            throw new IllegalArgumentException("Ungültige E-Mail");
        }
        
       List<MultipartFile> dateien = new ArrayList<>();
       if(unterlagen != null){
           for(MultipartFile datei : unterlagen){
               if (!datei.isEmpty()){
                   dateien.add(datei);
               }
           }
       }
       
       if (dateien.size() > 5 || dateien.size() == 0){
           throw new IllegalArgumentException("Es muss mindestens eine und maximal fünf Dateien hochgeladen werden.");
       }
       
       for (MultipartFile d : dateien){
           if (d.getSize() > 5 * 1024 * 1024){
               throw new IllegalArgumentException("Die Datei "+ d.getOriginalFilename() + " ist größer als 5 MB.");
           }
           
           
           if(!istPdf(d.getBytes())){
               throw new IllegalArgumentException("Die Datei " + d.getOriginalFilename() + " ist kein PDF.");
           }
       }
        
        Stellen s = stelleRepository.findById(stelleId)
                .orElseThrow(() -> new NichtGefundenException("Diese Stelle gibt es nicht."));
        // Auf geschlossene Stellen darf man sich nicht bewerben (z. B. über eine alte URL)
        if (s.getStellenStatus() != StellenStatus.OFFEN) {
            throw new NichtGefundenException("Diese Stelle ist nicht mehr ausgeschrieben.");
        }

        // Erst jetzt speichern – danach hat der Bewerber eine ID
        Bewerber b = bewerberRepository.save(bewerber);

        Bewerbung gespeichert = bewerbungRepository.save(new Bewerbung(b, s, anschreiben, gehaltswunsch));
        statusAenderungRepository.save(new StatusAenderung(b.getId(), stelleId, null, BewerbungStatus.EINGEGANGEN, null));

        // Unterlagen speichern – gleiche Transaktion, also alles oder nichts
        for (MultipartFile d : dateien){
            dokumentRepository.save(new Dokument(b.getId(), stelleId, d.getOriginalFilename(),
                    d.getContentType(), d.getSize(), d.getBytes()));
        }
        return gespeichert;
    }

    // Jede echte PDF-Datei beginnt mit den Zeichen "%PDF" ("Magic Number")
    private boolean istPdf(byte[] daten){
        return daten.length >= 4 && daten[0] == '%' && daten[1] == 'P' && daten[2] == 'D' && daten[3] == 'F';
    }

    public List<Dokument> dokumenteFinden(Long bewerberId, Long stellenId){
        return dokumentRepository.findByBewerberIdAndStellenIdOrderByHochgeladenAmAsc(bewerberId, stellenId);
    }

    public Dokument dokumentFinden(Long id){
        return dokumentRepository.findById(id)
                .orElseThrow(() -> new NichtGefundenException("Dieses Dokument gibt es nicht."));
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

