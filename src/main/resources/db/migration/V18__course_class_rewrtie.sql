ALTER TABLE course_class_timing
    ADD course_class_id BIGINT;

ALTER TABLE course_class_timing
    ALTER COLUMN course_class_id SET NOT NULL;

ALTER TABLE course_class_timing
    ADD CONSTRAINT FK_COURSE_CLASS_TIMING_ON_COURSE_CLASS FOREIGN KEY (course_class_id) REFERENCES course_classes (id);

ALTER TABLE course_class_timing
    ALTER COLUMN class_type SET NOT NULL;

ALTER TABLE course_class_timing
    ALTER COLUMN day SET NOT NULL;

ALTER TABLE course_class_timing
    ALTER COLUMN end_time SET NOT NULL;

ALTER TABLE course_class_timing
    ALTER COLUMN location_id SET NOT NULL;

ALTER TABLE course_class_timing
    ALTER COLUMN start_time SET NOT NULL;