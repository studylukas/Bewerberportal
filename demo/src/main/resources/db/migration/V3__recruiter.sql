create table recruiter (
    id            integer primary key,
    name          varchar(50) not null,
    email         varchar(50) not null,
    passwortHash  varchar(255) not null,
    rolle         ENUM('ADMIN', 'RECRUITER', 'GUEST') not null
);
