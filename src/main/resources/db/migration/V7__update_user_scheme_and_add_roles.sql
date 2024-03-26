CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    roles   VARCHAR(255)
);

ALTER TABLE users
    ADD is_account_non_expired BOOLEAN DEFAULT TRUE;

ALTER TABLE users
    ADD is_account_non_locked BOOLEAN DEFAULT TRUE;

ALTER TABLE users
    ADD is_credentials_non_expired BOOLEAN DEFAULT TRUE;

ALTER TABLE users
    ADD is_enabled BOOLEAN DEFAULT TRUE;

ALTER TABLE users
    ALTER COLUMN is_account_non_expired SET NOT NULL;

ALTER TABLE users
    ALTER COLUMN is_account_non_locked SET NOT NULL;

ALTER TABLE users
    ALTER COLUMN is_credentials_non_expired SET NOT NULL;

ALTER TABLE users
    ALTER COLUMN is_enabled SET NOT NULL;

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    RENAME
        COLUMN user_name
        TO username;