CREATE TABLE system
(
    system_id SERIAL PRIMARY KEY,
    name      TEXT NOT NULL UNIQUE
);
ALTER SEQUENCE system_system_id_seq RESTART 10000;