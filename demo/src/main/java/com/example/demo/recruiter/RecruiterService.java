package com.example.demo.recruiter;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NichtGefundenException;

@Service
public class RecruiterService {
    private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;


    public RecruiterService(RecruiterRepository recruiterRepository, PasswordEncoder passwordEncoder){
        this.recruiterRepository = recruiterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Recruiter registerRecruiter(String email, String passwort, String name, Rolle rolle){

        if (passwort == null || passwort.length() < 8) {throw new IllegalArgumentException("Passwort muss mindestens 8 Zeichen lang sein.");}
        
        if (recruiterRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("Diese E-Mail wird bereits verwendet");
        }

        Recruiter recruiter = new Recruiter();
        recruiter.setEmail(email);
        recruiter.setName(name);

        String hashedPassword = passwordEncoder.encode(passwort);
        recruiter.setPasswortHash(hashedPassword);
        recruiter.setRolle(rolle);

        return recruiterRepository.save(recruiter);
    }

    public Recruiter bearbeiter(String email){
       return recruiterRepository.findByEmail(email).orElseThrow(() -> new NichtGefundenException("Recruiter nicht gefunden."));
    }
    
    public List<Recruiter> alleRecruiter(){
        return recruiterRepository.findAll();
    }

}
