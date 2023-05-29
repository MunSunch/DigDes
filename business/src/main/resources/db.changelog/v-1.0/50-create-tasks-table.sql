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
);

alter table tasks
add constraint fk_tasks_projects foreign key(project_id)
    references projects(id);