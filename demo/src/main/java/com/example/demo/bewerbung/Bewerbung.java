package com.example.demo.bewerbung;

import java.time.LocalDateTime;

import com.example.demo.Stellen.Stellen;
import com.example.demo.bewerber.Bewerber;

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

    protected Bewerbung() {}

    public Bewerbung(Bewerber bewerber, Stellen stelle, String anschreiben) {
        this.id = new BewerbungId(bewerber.getId(), stelle.getId());
        this.bewerber = bewerber;
        this.stelle = stelle;
        this.anschreiben = anschreiben;
    }

    public void setStatus(BewerbungStatus neu) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }

    // Getter, plus Setter für status
}
