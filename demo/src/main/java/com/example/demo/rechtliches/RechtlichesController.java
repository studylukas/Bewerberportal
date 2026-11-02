package com.example.demo.rechtliches;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Rechtliche Pflichtseiten, die laut Projektbericht von jeder Seite aus erreichbar sein müssen
@Controller
public class RechtlichesController {

    @GetMapping("/impressum")
    public String impressum(){
        return "rechtliches/impressum";
    }

    @GetMapping("/datenschutz")
    public String datenschutz(){
        return "rechtliches/datenschutz";
    }
}
