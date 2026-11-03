package com.example.demo.bewerbung;

import java.time.LocalDateTime;

import com.example.demo.recruiter.Recruiter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "status_aenderung")
public class StatusAenderung {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bewerber_id", nullable = false)
    private Long bewerberId;
    @Column(name = "stellen_id", nullable = false)
    private Long stellenId;
    @Enumerated(EnumType.STRING)
    @Column (name = "von_status", length = 20)
    private BewerbungStatus vonStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "nach_status", nullable = false, length = 20)
    private BewerbungStatus nachStatus;
    @Column(name = "geaendert_am", nullable = false)
    private LocalDateTime geaendertAm;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;
    
    protected StatusAenderung(){}
    
    public StatusAenderung(Long bewerberId, Long stellenId, BewerbungStatus vonStatus, BewerbungStatus nachStatus, Recruiter recruiter){
        this.bewerberId = bewerberId;
        this.stellenId = stellenId;
        this.vonStatus = vonStatus;
        this.nachStatus = nachStatus;
        this.geaendertAm = LocalDateTime.now();
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

    public BewerbungStatus getVonStatus() {
        return vonStatus;
    }

    public BewerbungStatus getNachStatus() {
        return nachStatus;
    }

    public LocalDateTime getGeaendertAm() {
        return geaendertAm;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

}
