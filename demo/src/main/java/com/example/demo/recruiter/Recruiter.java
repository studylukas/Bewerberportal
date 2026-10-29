package com.example.demo.recruiter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "recruiter")
public class Recruiter {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) private Long id;
    @Column(length = 50, nullable = false) private String name;
    @Column(length = 50, nullable = false) private String email;
    @Column(length = 255, nullable = false) private String passwortHash;
    private String rolle;
    
    
    public Long getId(){
        return id;
    } 
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPasswortHash(String passwortHash) {
        this.passwortHash = passwortHash;
    }

    public String getPasswortHash() {
        return passwortHash;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getRolle() {
        return rolle;
    }
}
