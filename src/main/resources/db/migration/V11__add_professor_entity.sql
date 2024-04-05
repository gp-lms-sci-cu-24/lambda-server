CREATE TABLE professors
(
    id           BIGINT NOT NULL,
    professor_id BIGINT,
    CONSTRAINT pk_professors PRIMARY KEY (id)
);

ALTER TABLE professors
    ADD CONSTRAINT FK_PROFESSORS_ON_ID FOREIGN KEY (id) REFERENCES users (id);