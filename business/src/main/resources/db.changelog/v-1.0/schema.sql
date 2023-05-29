create table post_employees
(
    id   serial
        constraint pk_post_employees
            primary key,
    name varchar(50) not null
);
GO

insert into post_employees(name) values
('DEVELOPER'),
('MANAGER'),
('TESTER'),
('DESIGNER');
GO

create table accounts
(
    id       serial
        constraint pk_accounts
            primary key,
    login    varchar(50) not null,
    password varchar(50) not null
);
GO

create table status_employees
(
    id   serial
        constraint pk_status_employees
            primary key,
    name varchar(50) not null
);
GO

insert into status_employees(name) values
('ACTIVE'),
('REMOVED');
GO

create table employees
(
    id                  serial
        constraint pk_employees
            primary key,
    name                varchar(50) not null,
    lastname            varchar(50) not null,
    patronymic          varchar(50),
    post_id             integer
        constraint fk_employees_posts
            references post_employees,
    account_id          integer
        constraint fk_employees_accounts
            references accounts,
    email               varchar(80),
    status_employees_id integer     not null
        constraint fk_employees_status_employees
            references status_employees
);
GO

create table status_projects
(
    id   serial
        constraint pk_status_projects
            primary key,
    name varchar(50) not null
);
GO

insert into status_projects(name) values
('DRAFT'),
('DEVELOPING'),
('TESTING'),
('COMPLETED');
GO

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
GO

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
GO

create table roles
(
    id   serial
        constraint pk_roles
            primary key,
    name varchar(50) not null
);
GO

insert into roles(name) values
('DEVELOPER'),
('MANAGER'),
('TESTER'),
('DESIGNER'),
('LEAD');
GO

create table commands_to_employees
(
    id          serial
        constraint pk_commands_to_employees
            primary key,
    command_id  integer not null
        constraint fk_commandstoemployees_commands
            references commands,
    employee_id integer not null
        constraint fk_commandstoemployees_employees
            references employees,
    role_id     integer not null
        constraint fk_commandstoemployees_roles
            references roles
);
GO

create table status_tasks
(
    id   serial
        constraint pk_status_tasks
            primary key,
    name varchar(50) not null
);
GO

insert into status_tasks(name) values
('NEW'),
('WORKING'),
('COMPLETED'),
('CLOSE');

create table tasks
(
    id               serial
        constraint pk_tasks
            primary key,
    name             varchar(50) not null,
    description      text,
    employee_id      integer
        constraint fk_tasks_employees
            references employees,
    cost             money       not null,
    start_date       date        not null,
    last_change_date date        not null,
    end_date         date        not null,
    create_date      date        not null,
    status_id        integer     not null
        constraint fk_tasks_statustasks
            references status_tasks,
    project_id integer not null
        constraint fk_tasks_projects
            references projects
);

-- create table projects_to_tasks
-- (
--     id         serial
--         constraint pk_projects_to_tasks
--             primary key,
--     project_id integer
--         constraint fk_projectstotasks_to_projects
--             references projects,
--     task_id    integer
--         constraint fk_projectstotasks_to_tasks
--             references tasks
-- );