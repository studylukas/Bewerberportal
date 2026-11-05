package com.example.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// Stellt für ALLE Seiten Werte bereit, die das gemeinsame Layout braucht.
// @ModelAttribute-Methoden laufen automatisch vor jedem Controller-Aufruf.
@ControllerAdvice
public class NavigationAdvice {

    // true, wenn der eingeloggte Nutzer Administrator ist -> Link "Recruiter verwalten" im Layout.
    // Spring Security speichert Rollen mit dem Präfix "ROLE_" (aus ADMIN wird ROLE_ADMIN).
    @ModelAttribute("istAdmin")
    public boolean istAdmin(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
