alter table employees
drop constraint fk_employees_posts;
GO

alter table employees
drop constraint fk_employees_accounts;

GO

alter table employees
drop constraint fk_employees_status_employees;

GO
drop table employees;