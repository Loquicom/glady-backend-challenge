--liquibase formatted sql
--changeset test:01

INSERT INTO company
VALUES (0, 'test-company', 800);

INSERT INTO employee
VALUES (0, 'John', 'Doe', 0);

INSERT INTO deposit(amount, date, expire, type, em_id)
VALUES (100, '2021-10-10', '2022-10-10', 'GIFT', 0);
INSERT INTO deposit(amount, date, expire, type, em_id)
VALUES (80, '2021-02-02', '2022-02-02', 'GIFT', 0);
INSERT INTO deposit(amount, date, expire, type, em_id)
VALUES (60, '2022-04-04', '2023-03-01', 'MEAL', 0);
INSERT INTO deposit(amount, date, expire, type, em_id)
VALUES (40, '2021-01-01', '2022-03-01', 'MEAL', 0);
