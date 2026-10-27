package com.example.demo.recruiter;

import java.nio.file.OpenOption;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Stellen.Stellen;
import com.example.demo.Stellen.StellenRepository;
import com.example.demo.Stellen.StellenService;

@Service 
public class RecruiterService {
    private final RecruiterRepository recruiterRepository;
    private final StellenService stellenService;
    private final StellenRepository stellenRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    public RecruiterService(RecruiterRepository recruiterRepository, StellenService stellenService, StellenRepository stellenRepository, PasswordEncoder passwordEncoder){
        this.recruiterRepository = recruiterRepository;
        this.stellenService = stellenService;
        this.stellenRepository = stellenRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Recruiter registerRecruiter(String email, String passwortHash, String name){
        
        if (recruiterRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("Diese E-Mail wird bereits verwendet");
        }
        
        Recruiter recruiter = new Recruiter();
        recruiter.setEmail(email);
        recruiter.setName(name);
        
        String hashedPassword = passwordEncoder.encode(passwortHash);
        recruiter.setPasswortHash(hashedPassword);
        
        return recruiterRepository.save(recruiter);
    }
    
    public boolean login (String email, String passwortHash){
        Optional<Recruiter> recruiterOpt = recruiterRepository.findByEmail(email);
        
        if (recruiterOpt.isEmpty()){
            return false;
        }
        
        Recruiter recruiter = recruiterOpt.get();
        return passwordEncoder.matches(passwortHash, recruiter.getPasswortHash());
    }
    
    
    public Stellen stelleAnlegen(Stellen stellen){
        return stellenRepository.save(stellen);
    }
    
    
    
}
