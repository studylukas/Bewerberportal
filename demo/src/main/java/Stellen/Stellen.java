package Stellen;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity 
public class Stellen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (length = 150, nullable = false)
    private String bezeichnung;
    @Column (length = 1000, nullable = false)
    private String beschreibung;
    @Column (length = 100, nullable = false)
    private String bereich;
    @Column (name = "stellen_status", length = 100, nullable = false)
    private String stellenStatus;
    @Column (name = "erstellt_am")
    private Instant erstelltAm;
    
    protected Stellen (){}
    
    public Stellen(String bezeichnung, String beschreibung, String bereich, String stellenStatus){
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.bereich = bereich;
        this.stellenStatus = stellenStatus;
    }
    
    @PrePersist 
    void beimAnlegen(){
        this.erstelltAm = Instant.now();
    }
    
    public void setBezeichnung(String bezeichnung){
        this.bezeichnung = bezeichnung;
    }
    
}
