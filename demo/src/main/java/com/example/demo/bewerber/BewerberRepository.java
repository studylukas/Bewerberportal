package com.example.demo.bewerber;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BewerberRepository extends JpaRepository<Bewerber, Long>{
    Optional<Bewerber> findById(Long id);
}
