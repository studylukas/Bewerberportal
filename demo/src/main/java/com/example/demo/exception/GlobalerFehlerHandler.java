package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

// Zentrale Fehlerbehandlung: gilt für alle Controller der Anwendung
@ControllerAdvice
public class GlobalerFehlerHandler {

    @ExceptionHandler(NichtGefundenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String nichtGefunden(NichtGefundenException ex, Model model){
        model.addAttribute("meldung", ex.getMessage());
        return "fehler/nichtGefunden";
    }

    // Ungültige Eingaben aus den Prüfungen in den Services (z. B. kein PDF, fehlende Einwilligung)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String ungueltigeEingabe(IllegalArgumentException ex, Model model){
        model.addAttribute("titel", "Eingabe ungültig");
        model.addAttribute("meldung", ex.getMessage());
        return "fehler/fehler";
    }

    // Wird von Spring geworfen, bevor unser Code läuft – die Originalmeldung ist technisch und englisch
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String dateiZuGross(Model model){
        model.addAttribute("titel", "Dateien zu groß");
        model.addAttribute("meldung", "Bitte laden Sie nur PDF-Dateien mit je max. 5 MB und insgesamt max. 20 MB hoch.");
        return "fehler/fehler";
    }
}
