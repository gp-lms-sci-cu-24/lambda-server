
CREATE SEQUENCE IF NOT EXISTS announcement_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE announcements
(
    id          BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type        VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    edited_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_announcements PRIMARY KEY (id)
);

CREATE TABLE specific_announcements
(
    id      BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_specific_announcements PRIMARY KEY (id)
);

ALTER TABLE specific_announcements
    ADD CONSTRAINT FK_SPECIFIC_ANNOUNCEMENTS_ON_ID FOREIGN KEY (id) REFERENCES announcements (id);

ALTER TABLE specific_announcements
    ADD CONSTRAINT FK_SPECIFIC_ANNOUNCEMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
