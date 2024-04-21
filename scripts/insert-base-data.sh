#!/bin/bash

# Check if password argument is provided
if [ $# -ne 1 ]; then
    echo "Usage: $0 <password>"
    exit 1
fi

# Set the password from the argument
PG_PASSWORD=$1

# Set your database name
DB_NAME="lambda-server"

# Set your PostgreSQL username
PG_USER=postgres

# Construct the psql command to disconnect sessions from the database
DISCONNECT_COMMAND="PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d postgres -t -c \"SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname='$DB_NAME' AND pid <> pg_backend_pid();\""

# students table insert query
INSERT_STUDENTS_QUERY="
INSERT INTO public.students (id, first_name, father_name, grandfather_name, lastname, code, credit_hours, address, gpa,
                                                    level, gender, joining_year, department_id, credit_hours_semester)
                       VALUES (1, 'mohamed', 'atef', 'Abdelhakim', 'Shata', '2027115', 128, 'street', 3, 'LEVEL_4', 'MALE', '2020', 1, 18),
                        (2, 'beso', 'abo_beso', 'geodo_beso', 'besos', '2027069', 128, 'street', 3, 'LEVEL_4', 'FEMALE', '2020', 1, 18);
"


# Construct the psql command to insert the students
INSERT_STUDENTS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c $INSERT_STUDENTS_QUERY "


# locations table insert query
INSERT_LOCATIONS_QUERY="INSERT INTO public.locations (id, path, info, max_capacity)
                        VALUES (1, 'first floor math department', 'oldest building the collage', 500),
                         (2, 'first floor math department', '2nd oldest building the collage', 50);

"

# Construct the psql command to insert the locations
INSERT_LOCATIONS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_LOCATIONS_QUERY' "

# locations table insert query
INSERT_DEP_QUERY="INSERT INTO public.departments (id, name, info, image, code)
                  VALUES (1, 'Computer Science', 'Started in 1980''s', null, 'COMP');
"

# Construct the psql command to insert the locations
INSERT_DEP_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_DEP_QUERY' "

# courses table insert query
INSERT_COURSES_QUERY="INSERT INTO public.courses (id, name, code, info, credit_hours)
                      VALUES (1, 'calculus 1', 'MATH131', 'what is the functions and calculus introduction', 3),
                       (2, 'calculus 2', 'MATH132', 'how to use functions and differentiation and integration methods', 3),
                       (3, 'calculus 3', 'MATH133', 'real life usage and vector calculus', 4);
"

# Construct the psql command to insert the courses
INSERT_COURSES_COMMAND=" PGPASSWORD='$PG_PASSWORD'  psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_COURSES_QUERY' "

# course_prerequisites table insert query
INSERT_COURSE_PREREQUISITES_QUERY="INSERT INTO public.courses_course_prerequisites (course_id, course_prerequisites_id)
                                   VALUES (1, 2),
                                   (2, 3);
"

# Construct the psql command to insert the course prerequisites
INSERT_COURSE_PREREQUISITES_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_COURSE_PREREQUISITES_QUERY' "

# course_class table insert query
INSERT_COURSE_CLASS_QUERY="INSERT INTO public.course_classes (course_class_id, course_id, publish_date, course_semester, course_state,
                                                              max_capacity, number_of_students_registered, capacity_so_far, group_number_seq)
                           VALUES (1, 1, '2024-04-21 13:45:42.000000', 'SUMMER', 'ACTIVE', 100, 18, 50, 1),
                            (1, '2024-04-21 13:45:42.000000', 'SUMMER', 'ACTIVE', 100, 18, 50, 2),
                            (2, '2024-04-21 13:45:42.000000', 'SUMMER', 'ACTIVE', 100, 18, 50, 1),
                            (2, '2024-04-21 13:45:42.000000', 'SUMMER', 'ACTIVE', 100, 18, 50, 2);
"

# Construct the psql command to insert the course classes
INSERT_COURSE_CLASS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_COURSE_CLASS_QUERY' "


# timings table insert query
INSERT_TIMINGS_QUERY="INSERT INTO public.course_class_timing (id, class_type, day, start_time, end_time, location_id)
                      VALUES (1, 'THEORETICAL_LECTURE', 'SUNDAY', 12, 14, 1),
                       (2, 'TRAINING_SECTION', 'SUNDAY', 14, 16, 1),
                       (3, 'THEORETICAL_LECTURE', 'SUNDAY', 12, 14, 2),
                       (4, 'TRAINING_SECTION', 'SUNDAY', 14, 16, 2);
"

# Construct the psql command to insert the timings
INSERT_TIMINGS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_TIMINGS_QUERY' "

# timing_for_class table insert query
INSERT_TIMING_FOR_CLASS_QUERY="INSERT INTO public.timing_registers (id, course_class_course_class_id, course_class_timing_id, register_date)
                                                                 VALUES (1, 1, 1, '2024-04-21 13:42:14.000000'),
                                                                  (2, 1, 2, '2024-04-21 13:42:14.000000'),
                                                                  (3, 2, 3, '2024-04-21 13:42:14.000000'),
                                                                  (4, 2, 4, '2024-04-21 13:42:14.000000')"

# Construct the psql command to insert the timings for classes
INSERT_TIMING_FOR_CLASS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_TIMING_FOR_CLASS_QUERY' "

# registrations table insert query
INSERT_REGISTRATIONS_QUERY="INSERT INTO public.course_registers (course_register_id, course_class_id, student_id, register_date, grade)
                            VALUES (1, 1, 2027115, '2024-04-21 13:54:54.000000', -1),
                             (2, 2, 2027115, '2024-04-21 13:54:54.000000', -1);
"
# Construct the psql command to insert the registrations
INSERT_REGISTRATIONS_COMMAND=" PGPASSWORD='$PG_PASSWORD' psql -p 5434 -U $PG_USER -d lambda-server -c '$INSERT_REGISTRATIONS_QUERY' "

# Execute the commands
eval $INSERT_STUDENTS_COMMAND

