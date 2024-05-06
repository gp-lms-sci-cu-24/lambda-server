ALTER TABLE professors
    ADD degree VARCHAR(255);

ALTER TABLE course_classes_professors
    DROP CONSTRAINT fk_couclapro_on_course_class;

ALTER TABLE course_classes_professors
    DROP CONSTRAINT fk_couclapro_on_professor;

ALTER TABLE course_classes_professors
    ADD professors_id BIGINT;

ALTER TABLE course_classes_professors
    ADD course_class_course_class_id BIGINT;

ALTER TABLE course_classes_professors
    ALTER COLUMN course_class_course_class_id SET NOT NULL;

ALTER TABLE course_classes_professors
    ALTER COLUMN professors_id SET NOT NULL;

ALTER TABLE course_classes_professors
    DROP COLUMN course_classes_id;

ALTER TABLE course_classes_professors
    DROP COLUMN professor_id;

ALTER TABLE course_classes_professors
    ADD CONSTRAINT fk_couclapro_on_professor FOREIGN KEY (professors_id) REFERENCES professors (id);

ALTER TABLE course_classes_professors
    ADD CONSTRAINT fk_couclapro_on_course_class FOREIGN KEY (course_class_course_class_id) REFERENCES course_classes (course_class_id);