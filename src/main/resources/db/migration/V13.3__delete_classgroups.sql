ALTER TABLE course_class_groups
    DROP CONSTRAINT fk_course_class_groups_on_course_class;

DROP TABLE course_class_groups CASCADE;