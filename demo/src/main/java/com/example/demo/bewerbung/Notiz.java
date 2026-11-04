package com.example.demo.bewerbung;

import java.time.LocalDateTime;

import com.example.demo.recruiter.Recruiter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "notiz")
public class Notiz {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bewerber_id", nullable = false)
    private Long bewerberId;
    @Column (name = "stellen_id", nullable = false)
    private Long stellenId;
    @Column (nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(name = "erstellt_am", nullable = false)
    private LocalDateTime erstelltAm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "recruiter_id")
    private Recruiter recruiter;
    
    protected Notiz(){}
    
    public Notiz(Long bewerberId, Long stellenId, String text, Recruiter recruiter){
        this.bewerberId = bewerberId;
        this.stellenId = stellenId;
        this.text = text;
        this.erstelltAm = LocalDateTime.now();
        this.recruiter = recruiter;
    }

    public Long getId() {
        return id;
    }

    public Long getBewerberId() {
        return bewerberId;
    }

    public Long getStellenId() {
        return stellenId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getErstelltAm() {
        return erstelltAm;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

}

