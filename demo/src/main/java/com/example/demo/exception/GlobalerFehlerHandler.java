package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// Zentrale Fehlerbehandlung: gilt für alle Controller der Anwendung
@ControllerAdvice
public class GlobalerFehlerHandler {

    @ExceptionHandler(NichtGefundenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String nichtGefunden(NichtGefundenException ex, Model model){
        model.addAttribute("meldung", ex.getMessage());
        return "fehler/nichtGefunden";
    }
}
