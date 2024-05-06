ALTER TABLE course_classes_professors
    ADD course_class_id BIGINT;

ALTER TABLE course_classes_professors
    ADD professor_id BIGINT;

ALTER TABLE course_classes_professors
    ALTER COLUMN course_class_id SET NOT NULL;

ALTER TABLE course_classes_professors
    ALTER COLUMN professor_id SET NOT NULL;

ALTER TABLE course_classes_professors
    DROP COLUMN course_class_course_class_id;

ALTER TABLE course_classes_professors
    DROP COLUMN professors_id;