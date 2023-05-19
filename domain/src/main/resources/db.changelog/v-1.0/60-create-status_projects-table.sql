create table status_projects
(
    id   serial
        constraint pk_status_projects
            primary key,
    name varchar(50) not null
);