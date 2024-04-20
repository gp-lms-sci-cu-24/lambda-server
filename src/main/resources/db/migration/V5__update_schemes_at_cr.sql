CREATE TABLE course_classes_professors
(
    course_classes_id BIGINT NOT NULL,
    professor_id      BIGINT NOT NULL
);

ALTER TABLE locations
    ADD image VARCHAR(255);

ALTER TABLE locations
    ADD name VARCHAR(255);

ALTER TABLE locations
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE locations
    ADD CONSTRAINT uc_locations_name UNIQUE (name);

ALTER TABLE course_classes_professors
    ADD CONSTRAINT fk_couclapro_on_course_class FOREIGN KEY (course_classes_id) REFERENCES course_classes (course_class_id);

ALTER TABLE course_classes_professors
    ADD CONSTRAINT fk_couclapro_on_professor FOREIGN KEY (professor_id) REFERENCES professors (id);