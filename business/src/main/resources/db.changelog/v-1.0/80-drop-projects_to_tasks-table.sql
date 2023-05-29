alter table projects_to_tasks
drop constraint fk_projectsToTasks_to_projects;
GO
alter table projects_to_tasks
drop constraint fk_projectsToTasks_to_tasks;
GO
drop table projects_to_tasks;