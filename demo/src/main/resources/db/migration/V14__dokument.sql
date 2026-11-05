CREATE TABLE dokument (
    id              BIGINT Primary Key Auto_Increment,
    bewerber_id     Integer NOT NULL, 
    stellen_id      Integer NOT NULL, 
    dateiname       varchar(255) NOT NULL, 
    content_type    varchar(50),
    groesse         BIGINT, 
    inhalt          MEDIUMBLOB NOT NULL,
    hochgeladen_am  DATETIME NOT NULL
);

ALTER TABLE dokument ADD CONSTRAINT fk_dokument_bewerbung FOREIGN Key (bewerber_id, stellen_id) REFERENCES bewerbung(bewerber_id,stellen_id) ON DELETE CASCADE;