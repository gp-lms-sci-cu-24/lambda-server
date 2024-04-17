ALTER TABLE courseprerequisites
    DROP CONSTRAINT fk_courseprerequisites_on_course;

ALTER TABLE courseprerequisites
    DROP CONSTRAINT fk_courseprerequisites_on_prerequisite;

CREATE TABLE courses_course_prerequisites
(
    course_id               BIGINT NOT NULL,
    course_prerequisites_id BIGINT NOT NULL,
    CONSTRAINT pk_courses_courseprerequisites PRIMARY KEY (course_id, course_prerequisites_id)
);

ALTER TABLE courses_course_prerequisites
    ADD CONSTRAINT fk_coucoupre_on_course FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE courses_course_prerequisites
    ADD CONSTRAINT fk_coucoupre_on_courseprerequisites FOREIGN KEY (course_prerequisites_id) REFERENCES courses (id);


DROP SEQUENCE prerequisites_seq CASCADE;