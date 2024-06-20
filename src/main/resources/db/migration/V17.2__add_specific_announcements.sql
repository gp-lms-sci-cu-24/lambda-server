
CREATE TABLE specific_announcements
(
    id      BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_specific_announcements PRIMARY KEY (id)
);

ALTER TABLE announcements
    ADD type VARCHAR(255);

ALTER TABLE specific_announcements
    ADD CONSTRAINT FK_SPECIFIC_ANNOUNCEMENTS_ON_ID FOREIGN KEY (id) REFERENCES announcements (id);

ALTER TABLE specific_announcements
    ADD CONSTRAINT FK_SPECIFIC_ANNOUNCEMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

