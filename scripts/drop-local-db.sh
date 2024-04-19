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
# Construct the psql command to disconnect sessions from the database
DISCONNECT_COMMAND="PGPASSWORD='$PG_PASSWORD' psql -U $PG_USER -d postgres -t -c \"SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname='$DB_NAME' AND pid <> pg_backend_pid();\""

# Construct the psql command to drop the database with the password
DROP_COMMAND="PGPASSWORD='$PG_PASSWORD' psql -U $PG_USER -d postgres -c 'DROP DATABASE IF EXISTS \"$DB_NAME\";'"


# Construct the psql command to create the database with the password
CREATE_COMMAND="PGPASSWORD='$PG_PASSWORD' psql -U $PG_USER -d postgres -c 'CREATE DATABASE \"$DB_NAME\";'"

# Execute the command to disconnect sessions from the database
eval $DISCONNECT_COMMAND

# Execute the command to drop the database
eval $DROP_COMMAND

# Execute the command to create the database
eval $CREATE_COMMAND