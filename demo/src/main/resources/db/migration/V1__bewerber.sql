create table bewerber (
    id          Integer primary key,
    vorname     varchar(100) not null,
    nachname    varchar(100) not null,
    email       varchar(200) not null,
    telefon     varchar(20) not null
);