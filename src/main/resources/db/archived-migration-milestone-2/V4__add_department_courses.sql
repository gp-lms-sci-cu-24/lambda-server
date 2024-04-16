CREATE TABLE department_courses
(
    semester      VARCHAR(255),
    mandatory     BOOLEAN NOT NULL,
    department_id BIGINT  NOT NULL,
    course_id     BIGINT  NOT NULL,
    CONSTRAINT pk_department_courses PRIMARY KEY (department_id, course_id)
);

ALTER TABLE department_courses
    ADD CONSTRAINT FK_DEPARTMENT_COURSES_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE department_courses
    ADD CONSTRAINT FK_DEPARTMENT_COURSES_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id);