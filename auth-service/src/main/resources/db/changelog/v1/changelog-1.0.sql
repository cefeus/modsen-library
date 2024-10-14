-- liquibase formatted sql

--changeset cefeis:b59d5112-17c9-4b15-a236-93ec26ec7c69
CREATE TABLE users
(
    id  serial  NOT NULL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255)
);