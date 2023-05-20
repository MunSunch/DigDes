create table accounts
(
    id serial constraint pk_accounts primary key,
    login    varchar(50) not null,
    password varchar(50) not null
);