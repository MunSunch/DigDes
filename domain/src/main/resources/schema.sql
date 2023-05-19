create table post_employees
(
    id   serial
        constraint pk_post_employees
            primary key,
    name varchar(50) not null
);

create table accounts
(
    id       serial
        constraint pk_accounts
            primary key,
    login    varchar(50) not null,
    password varchar(50) not null
);

create table status_employees
(
    id   serial
        constraint pk_status_employees
            primary key,
    name varchar(50) not null
);

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

create table status_projects
(
    id   serial
        constraint pk_status_projects
            primary key,
    name varchar(50) not null
);

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

create table roles
(
    id   serial
        constraint pk_roles
            primary key,
    name varchar(50) not null
);

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

create table status_tasks
(
    id   serial
        constraint pk_status_tasks
            primary key,
    name varchar(50) not null
);

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
            references status_tasks
);

create table projects_to_tasks
(
    id         serial
        constraint pk_projects_to_tasks
            primary key,
    project_id integer
        constraint fk_projectstotasks_to_projects
            references projects,
    task_id    integer
        constraint fk_projectstotasks_to_tasks
            references tasks
);




-- вывести наименование и описание задачи по имени работника
select tasks.name,
       tasks.description
from tasks join employees on tasks.employee_id = employees.id
where employees.name='Munir';

-- вывести все задачи по коду проекта
select tasks.name,
       tasks.description
from tasks join projects_to_tasks on tasks.id = projects_to_tasks.task_id
           join projects on projects_to_tasks.project_id = projects.id
where projects.code = (select code
                       from projects
                       where id = 1)