CREATE TABLE refresh_tokens
(
    uid         VARCHAR(255)                NOT NULL,
    token       VARCHAR(255)                NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_agent  VARCHAR(255)                NOT NULL,
    ip_address  VARCHAR(255)                NOT NULL,
    user_id     BIGINT,
    CONSTRAINT pk_refresh_tokens PRIMARY KEY (uid)
);

ALTER TABLE refresh_tokens
    ADD CONSTRAINT FK_REFRESH_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);