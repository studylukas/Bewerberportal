ALTER TABLE bewerbung ADD COLUMN bewertung TINYINT; 

CREATE TABLE notiz(
    id              BIGINT Primary key AUTO_INCREMENT,
    bewerber_id     Integer NOT NULL,
    stellen_id      Integer NOT NULL,
    text            TEXT NOT NULL,
    erstellt_am     DATETIME NOT NULL,
    recruiter_id    BIGINT 
);

ALTER TABLE notiz ADD CONSTRAINT fk_notiz_bewertung FOREIGN Key (bewerber_id, stellen_id) REFERENCES bewerbung(bewerber_id,stellen_id) ON DELETE CASCADE;

ALTER TABLE notiz ADD CONSTRAINT fk_notiz_recruiter FOREIGN KEY (recruiter_id) REFERENCES recruiter(id) ON DELETE SET NULL;