package com.example.demo.bewerber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "bewerber")
public class Bewerber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String vorname;
    @Column (length = 100, nullable = false)
    private String nachname;
    @Column (length = 200, nullable = false)
    private String email;
    @Column (length = 20, nullable = false)
    private String telefon;
    
    protected Bewerber (){}
    
    public Bewerber(String vorname, String nachname, String email, String telefon){
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.telefon = telefon;
    }
    
    public void setVorname(String vorname){
        this.vorname = vorname;
    }
    
    public String getVorname(){
        return vorname;
    }
    
    public void setNachname(String nachname){
        this.nachname = nachname;
    }
    
    public String getNachname(){
        return nachname;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setTelefon(String telefon){
        this.telefon = telefon;
    }
    
    public String getTelefon(){
        return telefon;
    }
}
