create table commands
(
    id         serial
        constraint pk_commands
            primary key,
    code       integer not null,
    project_id integer
        constraint fk_commands_projects
            references projects
);