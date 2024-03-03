DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
    "user_id" TEXT,
    "password" TEXT,
    "first_name" TEXT,
    "last_name" TEXT,
    "email" TEXT,
    "national_id" TEXT,
    "photo_url" TEXT,
    "age" INT,
    "role" TEXT,
    PRIMARY KEY ("user_id")
);
DROP TABLE IF EXISTS "admins";
CREATE TABLE "admins" (
    "user_id" TEXT UNIQUE NOT NULL,
    "admin_id" TEXT,
    PRIMARY KEY ("admin_id"),
    FOREIGN KEY ("user_id") REFERENCES "users" ("user_id")
);