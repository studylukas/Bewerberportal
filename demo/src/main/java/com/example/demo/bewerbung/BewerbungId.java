package com.example.demo.bewerbung;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class BewerbungId implements Serializable{

    @Column(name = "bewerber_id")
    private Long bewerberId;
    
    @Column(name = "stellen_id")
    private Long stellenId;
    
    protected BewerbungId(){}
    
    public BewerbungId(Long bewerberId, Long stellenId){
        this.bewerberId = bewerberId;
        this.stellenId = stellenId;
    }

    public Long getBewerbId() {
        return bewerberId;
    }

    public Long getStellenId() {
        return stellenId;
    }
    
    @Override 
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof BewerbungId that)) return false;
        return Objects.equals(bewerberId, that.bewerberId) && Objects.equals(stellenId, that.stellenId);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(bewerberId, stellenId);
    }
}
