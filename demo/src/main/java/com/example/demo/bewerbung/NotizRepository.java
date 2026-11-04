package com.example.demo.bewerbung;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotizRepository extends JpaRepository<Notiz, Long>{
    List<Notiz> findByBewerberIdAndStellenIdOrderByErstelltAmDesc(Long bewerberId, Long stellenId);
}
