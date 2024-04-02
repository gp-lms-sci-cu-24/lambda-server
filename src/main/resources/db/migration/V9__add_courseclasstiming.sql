CREATE SEQUENCE IF NOT EXISTS timing_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE courseclasstiming
(
    id                          BIGINT NOT NULL,
    courseclass_course_class_id BIGINT,
    class_type                  VARCHAR(255),
    day                         VARCHAR(255),
    start_time                  BIGINT,
    end_time                    BIGINT,
    location_location_id        BIGINT,
    CONSTRAINT pk_courseclasstiming PRIMARY KEY (id)
);

ALTER TABLE courseclasstiming
    ADD CONSTRAINT FK_COURSECLASSTIMING_ON_COURSECLASS_COURSE_CLASS FOREIGN KEY (courseclass_course_class_id) REFERENCES course_classes (course_class_id);

ALTER TABLE courseclasstiming
    ADD CONSTRAINT FK_COURSECLASSTIMING_ON_LOCATION_LOCATION FOREIGN KEY (location_location_id) REFERENCES locations (location_id);