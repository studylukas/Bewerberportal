package com.example.demo.Stellen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StellenRepository extends JpaRepository<Stellen, Long>{

    // Jeder Filter ist optional: ist der Parameter null, ist die Bedingung automatisch erfüllt.
    // Das Stichwort wird in Bezeichnung und Beschreibung gesucht, unabhängig von Groß-/Kleinschreibung.
    // Bewerber sehen nur offene Stellen (Enum-Werte werden in JPQL mit vollständigem Namen angegeben).
    @Query("SELECT s FROM Stellen s " +
           "WHERE (:stichwort IS NULL " +
           "       OR LOWER(s.bezeichnung)  LIKE LOWER(CONCAT('%', :stichwort, '%')) " +
           "       OR LOWER(s.beschreibung) LIKE LOWER(CONCAT('%', :stichwort, '%'))) " +
           "AND (:bereich  IS NULL OR s.bereich  = :bereich) " +
           "AND (:standort IS NULL OR s.standort = :standort) " +
           "AND s.stellenStatus = com.example.demo.Stellen.StellenStatus.OFFEN " +
           "ORDER BY s.bezeichnung")
    List<Stellen> suchen(@Param("stichwort") String stichwort,
                         @Param("bereich") String bereich,
                         @Param("standort") String standort);

    // Werte für die Auswahllisten im Suchformular: jeder Bereich/Standort nur einmal, alphabetisch,
    // und nur aus offenen Stellen (sonst gäbe es Filter, die zu keinem Ergebnis führen)
    @Query("SELECT DISTINCT s.bereich FROM Stellen s " +
           "WHERE s.stellenStatus = com.example.demo.Stellen.StellenStatus.OFFEN " +
           "ORDER BY s.bereich")
    List<String> alleBereiche();

    @Query("SELECT DISTINCT s.standort FROM Stellen s " +
           "WHERE s.standort IS NOT NULL " +
           "AND s.stellenStatus = com.example.demo.Stellen.StellenStatus.OFFEN " +
           "ORDER BY s.standort")
    List<String> alleStandorte();
}
