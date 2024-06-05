CREATE TABLE storage_files
(
    id         BIGINT                      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    url        VARCHAR(255)                NOT NULL,
    file_type  VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_storage_files PRIMARY KEY (id)
);