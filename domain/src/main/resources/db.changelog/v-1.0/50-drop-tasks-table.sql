alter table tasks
drop constraint fk_tasks_employees;
GO

alter table tasks
drop constraint fk_tasks_statustasks;
GO

drop table tasks;