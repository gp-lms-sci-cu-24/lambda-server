CREATE SEQUENCE IF NOT EXISTS prerequisites_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE courseprerequisites
(
    id              BIGINT NOT NULL,
    course_id       BIGINT,
    prerequisite_id BIGINT,
    CONSTRAINT pk_courseprerequisites PRIMARY KEY (id)
);

ALTER TABLE courseprerequisites
    ADD CONSTRAINT FK_COURSEPREREQUISITES_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE courseprerequisites
    ADD CONSTRAINT FK_COURSEPREREQUISITES_ON_PREREQUISITE FOREIGN KEY (prerequisite_id) REFERENCES courses (id);