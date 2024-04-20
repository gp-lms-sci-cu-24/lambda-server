CREATE SEQUENCE IF NOT EXISTS contact_info_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE contact_info
(
    id        BIGINT NOT NULL,
    user_id   BIGINT,
    phone     VARCHAR(255),
    telephone VARCHAR(255),
    email     VARCHAR(255),
    CONSTRAINT pk_contact_info PRIMARY KEY (id)
);

ALTER TABLE contact_info
    ADD CONSTRAINT uc_contact_info_user UNIQUE (user_id);

ALTER TABLE contact_info
    ADD CONSTRAINT FK_CONTACT_INFO_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);