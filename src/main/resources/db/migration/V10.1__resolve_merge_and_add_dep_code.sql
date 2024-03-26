ALTER TABLE user_roles
DROP
CONSTRAINT fk_user_roles_on_user;

ALTER TABLE departments
    ADD code VARCHAR(255);

ALTER TABLE departments
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE departments
    ADD CONSTRAINT uc_departments_code UNIQUE (code);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_student FOREIGN KEY (user_id) REFERENCES students (id);