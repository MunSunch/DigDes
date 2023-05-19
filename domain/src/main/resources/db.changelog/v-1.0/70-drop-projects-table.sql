alter table projects
drop constraint uq_projects_code;
GO
alter table projects
drop constraint fk_projects_status_projects;
GO
drop table projects;