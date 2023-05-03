CREATE TABLE "users" (
                      id SERIAL PRIMARY KEY,
                      pwd VARCHAR(255) NOT NULL,
                      full_name VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP NOT NULL
);

INSERT INTO "users" (pwd, full_name, created_at, updated_at)
VALUES ('$2a$12$gcBV6MFJSz945kpEcEEvBORxoj3po3GTojRi4ktmCFYuHgnX8VPGe', 'fullname1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('$2a$12$HFvMnN3cykfi8lu7.m21Oee33xl/6hmiPiElMdg6o13Qs5Hrd8OMK', 'fullname2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
