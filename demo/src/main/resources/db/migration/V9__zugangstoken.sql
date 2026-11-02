Alter Table bewerbung ADD COLUMN zugang_token VARCHAR(36) NULL;

-- bestehende bekommen auch einen Token
UPDATE bewerbung set zugang_token = UUID() WHERE zugang_token is NULL;

ALTER TABLE bewerbung 
    MODIFY zugang_token VARCHAR(36) NOT NULL,
    ADD CONSTRAINT uk_bewerbung_zugang_token UNIQUE (zugang_token);