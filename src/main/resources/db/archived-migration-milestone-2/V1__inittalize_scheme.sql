CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE courses
(
    id           BIGINT       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    code         VARCHAR(255) NOT NULL,
    info         VARCHAR(255) NOT NULL,
    credit_hours INTEGER      NOT NULL,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

CREATE TABLE students
(
    id               BIGINT       NOT NULL,
    first_name       VARCHAR(255) NOT NULL,
    father_name      VARCHAR(255) NOT NULL,
    grandfather_name VARCHAR(255) NOT NULL,
    lastname         VARCHAR(255) NOT NULL,
    code             VARCHAR(255) NOT NULL,
    credit_hours     INTEGER          DEFAULT 0,
    address          VARCHAR(255),
    gpa              DOUBLE PRECISION DEFAULT 0,
    level            VARCHAR(255),
    CONSTRAINT pk_students PRIMARY KEY (id)
);

CREATE TABLE users
(
    id              BIGINT       NOT NULL,
    user_name       VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE courses
    ADD CONSTRAINT uc_courses_code UNIQUE (code);

ALTER TABLE students
    ADD CONSTRAINT uc_students_code UNIQUE (code);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_ID FOREIGN KEY (id) REFERENCES users (id);