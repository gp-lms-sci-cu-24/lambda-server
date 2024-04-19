ALTER TABLE courseclasstiming
    DROP CONSTRAINT fk_courseclasstiming_on_courseclass_course_class;

CREATE
SEQUENCE IF NOT EXISTS timing_registers_seq START
WITH 1 INCREMENT BY 50;

CREATE TABLE timing_registers
(
    id                           BIGINT NOT NULL,
    course_class_timing_id       BIGINT,
    course_class_course_class_id BIGINT,
    register_date                TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_timing_registers PRIMARY KEY (id)
);

ALTER TABLE timing_registers
    ADD CONSTRAINT FK_TIMING_REGISTERS_ON_COURSE_CLASS_COURSE_CLASS FOREIGN KEY (course_class_course_class_id) REFERENCES course_classes (course_class_id);

ALTER TABLE timing_registers
    ADD CONSTRAINT FK_TIMING_REGISTERS_ON_COURSE_CLASS_TIMING FOREIGN KEY (course_class_timing_id) REFERENCES courseclasstiming (id);

ALTER TABLE courseclasstiming
    DROP COLUMN courseclass_course_class_id;