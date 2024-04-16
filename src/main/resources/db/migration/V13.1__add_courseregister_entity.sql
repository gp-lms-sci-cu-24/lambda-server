CREATE
SEQUENCE IF NOT EXISTS course_registers_seq START
WITH 1 INCREMENT BY 50;

CREATE TABLE course_registers
(
    course_register_id BIGINT NOT NULL,
    course_class_id    BIGINT,
    student_id         BIGINT,
    register_date      TIMESTAMP WITHOUT TIME ZONE,
    grade              BIGINT,
    CONSTRAINT pk_course_registers PRIMARY KEY (course_register_id)
);

ALTER TABLE course_registers
    ADD CONSTRAINT FK_COURSE_REGISTERS_ON_COURSE_CLASS FOREIGN KEY (course_class_id) REFERENCES course_classes (course_class_id);

ALTER TABLE course_registers
    ADD CONSTRAINT FK_COURSE_REGISTERS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);