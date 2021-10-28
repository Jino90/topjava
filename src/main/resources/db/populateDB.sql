DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq1 RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (description, calories) values ('eda', 231);
INSERT INTO meals (datetime, description, calories) values ('2021-10-28 04:01:52.47104', 'ewe_eda', 543);
INSERT INTO meals (datetime, description, calories) values ('2021-10-28 04:02:52.47104', 'eda_edy', 543);
