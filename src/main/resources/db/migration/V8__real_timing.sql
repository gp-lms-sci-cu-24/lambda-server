ALTER TABLE course_class_timing
    DROP COLUMN end_time;

ALTER TABLE course_class_timing
    DROP COLUMN start_time;

ALTER TABLE course_class_timing
    ADD end_time time WITHOUT TIME ZONE;

ALTER TABLE course_class_timing
    ADD start_time time WITHOUT TIME ZONE;