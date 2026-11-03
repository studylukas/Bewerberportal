ALTER TABLE recruiter  MODIFY id BIGINT NOT NULL AUTO_INCREMENT;

Create Table status_aenderung(
    id              INTEGER Primary key Auto_Increment,
    bewerber_id     Integer NOT NULL,
    stellen_id      Integer NOT NULL,
    von_status      varchar(20),
    nach_status     varchar(20) NOT NULL, 
    geaendert_am    DATETIME NOT NULL,
    recruiter_id    BIGINT
);

    ALTER TABLE status_aenderung ADD CONSTRAINT fk_bewerber_stelle FOREIGN Key (bewerber_id, stellen_id) REFERENCES bewerbung(bewerber_id,stellen_id) ON DELETE CASCADE;

    ALTER TABLE status_aenderung ADD CONSTRAINT fk_status_recruiter FOREIGN KEY (recruiter_id) REFERENCES recruiter(id) ON DELETE SET NULL;
