create table commands_to_employees
(
    id          serial
        constraint pk_commands_to_employees
            primary key,
    command_id  integer not null
        constraint fk_commandsToEmployees_commands
            references commands,
    employee_id integer not null
        constraint fk_commandsToEmployees_employees
            references employees,
    role_id     integer not null
        constraint fk_commandsToEmployees_roles
            references roles
);