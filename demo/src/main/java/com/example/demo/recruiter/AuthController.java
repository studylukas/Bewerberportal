package com.example.demo.recruiter;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtUtil;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
    private final RecruiterService recruiterService;
    private final JwtUtil jwtUtil;
    
    public AuthController(RecruiterService recruiterService, JwtUtil jwtUtil){
        this.recruiterService = recruiterService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        boolean isAuthenticated = recruiterService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated){
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsche E-Mail oder Passwort.");
        }
    }
    
class LoginRequest {
    private String email;
    private String password; 

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

}
