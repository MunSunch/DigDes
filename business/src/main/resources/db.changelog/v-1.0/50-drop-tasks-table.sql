alter table tasks
drop constraint fk_tasks_employees;
GO

alter table tasks
drop constraint fk_tasks_statustasks;
GO

alter table tasks
drop constraint fk_tasks_projects;
GO

drop table tasks;