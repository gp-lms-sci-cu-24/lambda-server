CREATE
SEQUENCE IF NOT EXISTS professors_seq START
WITH 1 INCREMENT BY 50;

CREATE TABLE professors
(
    professor_id BIGINT NOT NULL,
    id           BIGINT,
    CONSTRAINT pk_professors PRIMARY KEY (professor_id)
);

ALTER TABLE professors
    ADD CONSTRAINT uc_professors_id UNIQUE (id);

ALTER TABLE professors
    ADD CONSTRAINT FK_PROFESSORS_ON_ID FOREIGN KEY (id) REFERENCES users (id);