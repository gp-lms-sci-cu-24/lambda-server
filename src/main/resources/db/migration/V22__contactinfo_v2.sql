

CREATE SEQUENCE IF NOT EXISTS contact_info_types_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE contact_info_types
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(255) NOT NULL,
    details VARCHAR(255),
    CONSTRAINT pk_contact_info_types PRIMARY KEY (id)
);

ALTER TABLE contact_info
    ADD contact_info_type_id BIGINT;

ALTER TABLE contact_info
    ADD value VARCHAR(255);

ALTER TABLE contact_info
    ALTER COLUMN value SET NOT NULL;

ALTER TABLE contact_info_types
    ADD CONSTRAINT uc_contact_info_types_name UNIQUE (name);

ALTER TABLE contact_info
    ADD CONSTRAINT FK_CONTACT_INFO_ON_CONTACT_INFO_TYPE FOREIGN KEY (contact_info_type_id) REFERENCES contact_info_types (id);


ALTER TABLE contact_info
DROP
COLUMN email;

ALTER TABLE contact_info
DROP
COLUMN phone;

ALTER TABLE contact_info
DROP
COLUMN telephone;
