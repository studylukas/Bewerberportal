create table bewerberStelle (
    bewerber_id         integer,
    stellen_id          integer,

    Primary Key (bewerber_id, stellen_id)
);
    

Alter Table bewerberStelle Add CONSTRAINT fk_bewerber foreign Key (bewerber_id) References bewerber(id) On Delete Cascade;
Alter Table bewerberStelle Add CONSTRAINT fk_stelle foreign key (stellen_id) References stellen(id) On delete Cascade;
