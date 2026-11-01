package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.recruiter.Recruiter;
import com.example.demo.recruiter.RecruiterRepository;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            
            
            // Hier wird die Kette ordnungsgemäß mit einem Semikolon abgeschlossen:
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers("/", "/index", "/login", "/error").permitAll()
                .requestMatchers("/favicon.ico", "/styles.css", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()

            )
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/startseite", true).permitAll());

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService(
            RecruiterRepository recruiterRepository) {

        return email -> {

            System.out.println("Loginversuch: " + email);

            Recruiter recruiter = recruiterRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "Recruiter nicht gefunden: " + email));

            System.out.println("Recruiter gefunden");

            return org.springframework.security.core.userdetails.User
                    .withUsername(recruiter.getEmail())
                    .password(recruiter.getPasswortHash())
                    .roles(recruiter.getRolle().name())
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
CommandLineRunner testPassword() {
    return args -> {
        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        System.out.println(
                encoder.encode("passwort123"));
    };
}
}