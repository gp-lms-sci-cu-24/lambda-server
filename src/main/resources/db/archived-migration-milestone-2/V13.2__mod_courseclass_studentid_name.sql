ALTER TABLE course_classes
    DROP CONSTRAINT fk_course_classes_on_id;

ALTER TABLE course_classes
    ADD course_id BIGINT;

ALTER TABLE course_classes
    ADD CONSTRAINT FK_COURSE_CLASSES_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE course_classes
    DROP COLUMN id;