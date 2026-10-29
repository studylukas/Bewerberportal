create table recruiter (
    id            integer primary key,
    name          varchar(50) not null,
    email         varchar(50) not null,
    passwort_hash varchar(255) not null,
    rolle         varchar(20) not null
);
