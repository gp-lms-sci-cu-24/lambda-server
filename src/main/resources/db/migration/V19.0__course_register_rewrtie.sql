CREATE SEQUENCE IF NOT EXISTS course_register_log_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS course_register_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS course_result_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE course_register_log
(
    id          BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    action      VARCHAR(255)                NOT NULL,
    student_id  BIGINT                      NOT NULL,
    user_id     BIGINT                      NOT NULL,
    description VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_course_register_log PRIMARY KEY (id)
);

CREATE TABLE course_register_sessions
(
    id              VARCHAR(255)                NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    student_id      BIGINT                      NOT NULL,
    course_class_id BIGINT                      NOT NULL,
    CONSTRAINT pk_course_register_sessions PRIMARY KEY (id)
);

CREATE TABLE course_result
(
    id              BIGINT                      NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    grade           INTEGER      DEFAULT 0,
    course_class_id BIGINT                      NOT NULL,
    rate            VARCHAR(255) DEFAULT 'FAIL',
    state           VARCHAR(255) DEFAULT 'FAILED',
    student_id      BIGINT                      NOT NULL,
    CONSTRAINT pk_course_result PRIMARY KEY (id)
);

ALTER TABLE course_registers
    ADD created_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE course_registers
    ADD id BIGINT;

ALTER TABLE course_registers
    ADD updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE course_registers
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE course_register_log
    ADD CONSTRAINT FK_COURSE_REGISTER_LOG_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE course_register_log
    ADD CONSTRAINT FK_COURSE_REGISTER_LOG_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE course_register_sessions
    ADD CONSTRAINT FK_COURSE_REGISTER_SESSIONS_ON_COURSE_CLASS FOREIGN KEY (course_class_id) REFERENCES course_classes (id);

ALTER TABLE course_register_sessions
    ADD CONSTRAINT FK_COURSE_REGISTER_SESSIONS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE course_result
    ADD CONSTRAINT FK_COURSE_RESULT_ON_COURSE_CLASS FOREIGN KEY (course_class_id) REFERENCES course_classes (id);

ALTER TABLE course_result
    ADD CONSTRAINT FK_COURSE_RESULT_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE course_registers
    DROP COLUMN course_register_id;

ALTER TABLE course_registers
    DROP COLUMN grade;

ALTER TABLE course_registers
    DROP COLUMN number_of_failed;

ALTER TABLE course_registers
    DROP COLUMN rate;

ALTER TABLE course_registers
    DROP COLUMN register_date;

ALTER TABLE course_registers
    DROP COLUMN state;

DROP SEQUENCE course_registers_seq CASCADE;

ALTER TABLE course_registers
    ALTER COLUMN course_class_id SET NOT NULL;

ALTER TABLE course_registers
    ALTER COLUMN student_id SET NOT NULL;

ALTER TABLE course_registers
    ADD CONSTRAINT pk_course_registers PRIMARY KEY (id);