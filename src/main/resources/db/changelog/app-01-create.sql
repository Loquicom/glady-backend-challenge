--liquibase formatted sql
--changeset app:01

CREATE TABLE COMPANY
(
    co_id   serial,
    name    varchar(255) not null,
    balance int default 0,
    PRIMARY KEY (co_id)
);

CREATE TABLE EMPLOYEE
(
    em_id     serial,
    firstname varchar(255) not null,
    lastname  varchar(255) not null,
    co_id     serial,
    PRIMARY KEY (em_id)
);

CREATE TABLE DEPOSIT
(
    de_id  serial,
    amount int        not null,
    date   date       not null,
    expire date       not null,
    type   varchar(5) not null,
    em_id  serial,
    PRIMARY KEY (de_id)
);

ALTER TABLE EMPLOYEE
    ADD FOREIGN KEY (co_id) REFERENCES COMPANY (co_id);
ALTER TABLE DEPOSIT
    ADD FOREIGN KEY (em_id) REFERENCES EMPLOYEE (em_id);
