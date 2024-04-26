CREATE SEQUENCE IF NOT EXISTS academic_advisor_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE academic_advisor
(
    id         BIGINT       NOT NULL,
    advisor_id BIGINT       NOT NULL,
    user_id    BIGINT       NOT NULL,
    type       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_academic_advisor PRIMARY KEY (id)
);

ALTER TABLE academic_advisor
    ADD CONSTRAINT uc_academic_advisor_user UNIQUE (user_id);

ALTER TABLE academic_advisor
    ADD CONSTRAINT FK_ACADEMIC_ADVISOR_ON_ADVISOR FOREIGN KEY (advisor_id) REFERENCES professors (id);

ALTER TABLE academic_advisor
    ADD CONSTRAINT FK_ACADEMIC_ADVISOR_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);