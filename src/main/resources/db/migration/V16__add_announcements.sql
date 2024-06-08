
CREATE SEQUENCE IF NOT EXISTS announcement_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE announcements
(
    id          BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    timestamp   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_announcements PRIMARY KEY (id)
);
