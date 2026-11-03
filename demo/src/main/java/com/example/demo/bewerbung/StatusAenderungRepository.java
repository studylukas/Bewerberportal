package com.example.demo.bewerbung;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusAenderungRepository extends JpaRepository<StatusAenderung, Long>{
    List<StatusAenderung> findByBewerberIdAndStellenIdOrderByGeaendertAmAsc(Long bewerberId, Long stellenId);
    List<StatusAenderung> findByNachStatusAndGeaendertAmBefore(BewerbungStatus nachStatus, LocalDateTime geaendertAm);
}
