package com.example.demo.recruiter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long>{
    Optional<Recruiter> findByEmail(String email);
}
