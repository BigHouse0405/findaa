CREATE TABLE "users" (
                      id SERIAL PRIMARY KEY,
                      pwd VARCHAR(255) NOT NULL,
                      fullname VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP NOT NULL
);

INSERT INTO "users" (pwd, fullname, created_at, updated_at)
VALUES ('pwd1', 'fullname1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('pwd2', 'fullname2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
