CREATE DATABASE GLADY;
\c GLADY;

CREATE TABLE COMPANY (
  co_id serial,
  name varchar(255),
  balance decimal,
  PRIMARY KEY (co_id)
);

CREATE TABLE EMPLOYEE (
  em_id serial,
  firstname varchar(255),
  lastname varchar(255),
  co_id serial,
  PRIMARY KEY (em_id)
);

CREATE TABLE DEPOSIT (
  de_id serial,
  amount decimal,
  date date,
  expire date,
  type varchar(5),
  em_id serial,
  PRIMARY KEY (de_id)
);

ALTER TABLE EMPLOYEE ADD FOREIGN KEY (co_id) REFERENCES COMPANY (co_id);
ALTER TABLE DEPOSIT ADD FOREIGN KEY (em_id) REFERENCES EMPLOYEE (em_id);