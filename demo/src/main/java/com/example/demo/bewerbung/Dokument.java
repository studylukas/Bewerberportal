package com.example.demo.bewerbung;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity 
@Table (name = "dokument")
public class Dokument {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bewerber_id", nullable = false)
    private Long bewerberId;
    @Column (name = "stellen_id", nullable = false)
    private Long stellenId;
    @Column (length = 255, nullable = false)
    private String dateiname;
    @Column(name = "content_type", length = 50)
    private String contentType;
    private Long groesse;
    @Lob
    @Column(columnDefinition ="MEDIUMBLOB", nullable = false)
    private byte[] inhalt;
    @Column (name = "hochgeladen_am", nullable = false)
    private LocalDateTime hochgeladenAm = LocalDateTime.now();
    
    protected Dokument(){}
    
    public Dokument(Long bewerberId, Long stellenId, String dateiname, String contentType, Long groesse, byte[] inhalt){
        this.bewerberId = bewerberId;
        this.stellenId = stellenId;
        this.dateiname = dateiname;
        this.contentType = contentType;
        this.groesse = groesse;
        this.inhalt = inhalt;        
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

    public String getDateiname() {
        return dateiname;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getGroesse() {
        return groesse;
    }

    public byte[] getInhalt() {
        return inhalt;
    }

}
