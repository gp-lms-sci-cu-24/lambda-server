ALTER TABLE professors
    ADD email VARCHAR(255);

ALTER TABLE professors
    ADD first_name VARCHAR(255);

ALTER TABLE professors
    ADD gender VARCHAR(255);

ALTER TABLE professors
    ADD last_name VARCHAR(255);

ALTER TABLE professors
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE professors
    ALTER COLUMN first_name SET NOT NULL;

ALTER TABLE professors
    ALTER COLUMN gender SET NOT NULL;

ALTER TABLE professors
    ALTER COLUMN last_name SET NOT NULL;

ALTER TABLE professors
    ALTER COLUMN degree SET NOT NULL;