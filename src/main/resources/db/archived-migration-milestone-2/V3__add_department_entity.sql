CREATE SEQUENCE IF NOT EXISTS department_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE departments
(
    id    BIGINT       NOT NULL,
    name  VARCHAR(255) NOT NULL,
    info  VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    CONSTRAINT pk_departments PRIMARY KEY (id)
);

ALTER TABLE students
    ADD department_id BIGINT;

ALTER TABLE departments
    ADD CONSTRAINT uc_departments_name UNIQUE (name);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id);