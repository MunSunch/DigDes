create table status_tasks
(
    id serial
        constraint pk_status_tasks
            primary key,
    name varchar(50) not null
);
