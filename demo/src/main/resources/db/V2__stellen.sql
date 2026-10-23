create table stellen (
    id              Integer primary key,
    bezeichnung     varchar(150) not null,
    beschreibung    varchar(1000) not null,
    bereich         varchar(100) not null,
    stellen_status  varchar(100) not null,
    erstellt_am     Timestamp default now()
);