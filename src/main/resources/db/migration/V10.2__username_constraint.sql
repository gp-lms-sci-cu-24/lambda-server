ALTER TABLE user_roles
    DROP CONSTRAINT fk_user_roles_on_student;

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);