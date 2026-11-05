package com.example.demo.bewerbung;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DokumentRepository extends JpaRepository<Dokument, Long>{
    List<Dokument> findByBewerberIdAndStellenIdOrderByHochgeladenAmAsc(Long bewerberId, Long stellenId);
}
