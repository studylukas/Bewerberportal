package com.example.demo.bewerbung;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.Stellen.Stellen;
import com.example.demo.bewerber.Bewerber;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "bewerbung")
public class Bewerbung {

    @EmbeddedId
    private BewerbungId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bewerberId")
    @JoinColumn(name = "bewerber_id")
    private Bewerber bewerber;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stellenId")
    @JoinColumn(name = "stellen_id")
    private Stellen stelle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BewerbungStatus status = BewerbungStatus.EINGEGANGEN;

    @Column(name = "beworben_am", nullable = false)
    private LocalDateTime beworbenAm = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String anschreiben;
    
    @Column(name = "einwilligung_am")
    private LocalDateTime einwilligungAm;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal gehaltswunsch;
    
    @Column(name = "zugang_token", nullable = false, unique = true, length = 36)
    private String zugangToken;
    
    private Integer bewertung;

    protected Bewerbung() {}

    public Bewerbung(Bewerber bewerber, Stellen stelle, String anschreiben, BigDecimal gehaltswunsch) {
        this.id = new BewerbungId(bewerber.getId(), stelle.getId());
        this.bewerber = bewerber;
        this.stelle = stelle;
        this.anschreiben = anschreiben;
        this.einwilligungAm = LocalDateTime.now();
        this.gehaltswunsch = gehaltswunsch;
        this.zugangToken = UUID.randomUUID().toString();
        }

    public BewerbungId getId() {
        return id;
    }

    public Bewerber getBewerber() {
        return bewerber;
    }

    public Stellen getStelle() {
        return stelle;
    }

    public BewerbungStatus getStatus() {
        return status;
    }

    public void setStatus(BewerbungStatus neu) {
        this.status = neu;
    }

    public LocalDateTime getBeworbenAm() {
        return beworbenAm;
    }

    public String getAnschreiben() {
        return anschreiben;
    }

    public LocalDateTime getEinwilligungAm() {
        return einwilligungAm;
    }
    
    public BigDecimal getGehaltswunsch(){
        return gehaltswunsch;
    }
    
    public void setGehaltswunsch(BigDecimal gehaltswunsch){
        this.gehaltswunsch = gehaltswunsch;
    }

    public String getZugangToken() {
        return zugangToken;
    }
    
    public void setBewertung(Integer bewertung){
        this.bewertung = bewertung;
    }
    
    public Integer getBewertung(){
        return bewertung;
    }
}
