package com.example.demo.exception;

public class NichtGefundenException extends RuntimeException {
    public NichtGefundenException(String meldung){
        super(meldung);
    }
}
