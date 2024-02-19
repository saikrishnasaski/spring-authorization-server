create database auth_server;

use auth_server;

create table users(
    id integer not null primary key,
    username varchar(50) not null unique,
    email varchar(100) not null unique,
    pwd varchar(500) not null,
    enabled boolean not null
);

create table authorities(
    id integer not null primary key,
    authority varchar2(60) not null,
    user integer not null
);