create table roles
(
    id   serial
        constraint pk_roles
            primary key,
    name varchar(50) not null
);