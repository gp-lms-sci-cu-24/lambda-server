ALTER TABLE course_classes
    DROP COLUMN publish_date;

ALTER TABLE course_classes
    ADD publish_date TIMESTAMP WITHOUT TIME ZONE;