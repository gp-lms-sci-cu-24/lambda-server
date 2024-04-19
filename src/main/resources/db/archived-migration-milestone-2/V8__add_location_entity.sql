CREATE SEQUENCE IF NOT EXISTS locations_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE locations
(
    location_id   BIGINT       NOT NULL,
    location_path VARCHAR(255) NOT NULL,
    location_info VARCHAR(255),
    max_capacity  INTEGER      NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (location_id)
);