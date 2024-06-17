
ALTER TABLE announcements
    ADD created_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE announcements
    ADD edited_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE announcements
DROP
COLUMN edited_timestamp;

ALTER TABLE announcements
DROP
COLUMN timestamp;
