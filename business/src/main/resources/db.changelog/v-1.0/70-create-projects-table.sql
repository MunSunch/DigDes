create table projects
(
    id                serial
        constraint pk_projects
            primary key,
    code              varchar(50) not null
        constraint uq_projects_code
            unique,
    name              varchar(50) not null,
    description       text,
    status_project_id integer     not null
        constraint fk_projects_status_projects
            references status_projects
);