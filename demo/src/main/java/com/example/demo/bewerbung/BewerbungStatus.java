package com.example.demo.bewerbung;

// Reihenfolge entspricht der Pipeline aus dem Projektbericht (Abb. 2),
// damit die Auswahl im Formular in Prozessreihenfolge erscheint.
public enum BewerbungStatus {
    EINGEGANGEN("Eingegangen"),
    IN_PRUEFUNG("In Prüfung"),
    GEANTWORTET("Geantwortet"),
    EINGELADEN("Eingeladen"),
    GESPRAECH("Gespräch"),
    ANGEBOT("Angebot"),
    EINGESTELLT("Eingestellt"),
    ABGELEHNT("Abgelehnt");

    private final String anzeigename;

    BewerbungStatus(String anzeigename) {
        this.anzeigename = anzeigename;
    }

    public String getAnzeigename() {
        return anzeigename;
    }

    // Bootstrap-Klasse für das farbige Status-Badge in den Templates
    public String getBadgeKlasse() {
        return switch (this) {
            case EINGEGANGEN -> "bg-primary";
            case EINGESTELLT -> "bg-success";
            case ABGELEHNT   -> "bg-danger";
            default          -> "bg-warning text-dark";
        };
    }
}
