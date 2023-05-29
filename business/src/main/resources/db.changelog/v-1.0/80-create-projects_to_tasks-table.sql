create table projects_to_tasks
(
    id         serial
        constraint pk_projects_to_tasks
            primary key,
    project_id integer
        constraint fk_projectsToTasks_to_projects
            references projects,
    task_id    integer
        constraint fk_projectsToTasks_to_tasks
            references tasks
);