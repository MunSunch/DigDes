create table employees
(
    id serial constraint pk_employees primary key,
    name varchar(50) not null,
    lastname varchar(50) not null,
    patronymic varchar(50),
    post_id integer constraint fk_employees_posts references post_employees,
    account_id integer constraint fk_employees_accounts references accounts,
    email varchar(80),
    status_employees_id integer not null
        constraint fk_employees_status_employees references status_employees
);