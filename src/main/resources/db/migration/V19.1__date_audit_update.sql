ALTER TABLE course_classes
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE course_classes
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;

ALTER TABLE course_registers
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE course_registers
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;

ALTER TABLE course_register_log
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE course_register_log
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;

ALTER TABLE course_register_sessions
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE course_register_sessions
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;

ALTER TABLE course_result
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE course_result
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;

ALTER TABLE storage_files
    ALTER COLUMN created_at
        SET DATA TYPE timestamp with time zone USING created_at::timestamp with time zone;
ALTER TABLE storage_files
    ALTER COLUMN updated_at
        SET DATA TYPE timestamp with time zone USING updated_at::timestamp with time zone;
