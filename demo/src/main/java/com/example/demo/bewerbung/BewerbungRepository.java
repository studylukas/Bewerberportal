package com.example.demo.bewerbung;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BewerbungRepository extends JpaRepository<Bewerbung, BewerbungId> {
    List<Bewerbung> findByIdBewerberId(Long bewerberId);
    List<Bewerbung> findByIdStellenId(Long stellenId);
    Optional<Bewerbung> findByZugangToken(String zugangToken);
}
