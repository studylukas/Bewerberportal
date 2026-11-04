package com.example.demo.Stellen;

public enum StellenStatus {
    OFFEN("Offen"),
    GESCHLOSSEN("Geschlossen");
    
    private final String anzeigename;
    
    StellenStatus(String anzeigename){
        this.anzeigename = anzeigename;
    }
    
    public String getAnzeigename() {
        return anzeigename;
    }
    
    public String getBadgeKlasse() {
        return switch (this) {
            case OFFEN -> "bg-primary";
            case GESCHLOSSEN -> "bg-secondary";
            default          -> "bg-warning text-dark";
        };
    }
}
