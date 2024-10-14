-- liquibase formatted sql

-- changeset cefeis:879c903c-2fdc-40c5-91d1-45eed21fe08d
CREATE TABLE book_reservations
(
    id SERIAL NOT NULL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    borrowing_time TIMESTAMP,
    return_time TIMESTAMP
);