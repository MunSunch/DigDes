INSERT INTO public.accounts (id, login, password) VALUES (DEFAULT, 'admin', '$2a$12$oo/pzFqSDHmHP4E6Z/WguekeqNzifNWkFuoE1ykFhvEGEMbb/h/ta');
GO

INSERT INTO public.employees (id, name, lastname, patronymic, post_id, account_id, email, status_employees_id) VALUES (DEFAULT, 'admin', 'admin', null, 2, 1, null, 1);