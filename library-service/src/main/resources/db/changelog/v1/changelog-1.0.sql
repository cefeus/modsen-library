-- liquibase formatted sql

--changeset cefeis:7100c877-5846-4359-894e-72544f330bf1
CREATE TABLE authors
(
    id  serial  NOT NULL PRIMARY KEY,
    name VARCHAR(255)
);

--changeset cefeis:a6a30748-8701-4ae0-a051-7929f4de27b9
CREATE TABLE books
(
    id serial  NOT NULL  PRIMARY KEY,
    isbn VARCHAR(255),
    name VARCHAR(255),
    description VARCHAR(255)
);

--changeset cefeis:640b65e7-a8b8-41e6-bc0c-bdbb7dad96f8
CREATE TABLE genres
(
    id serial  NOT NULL PRIMARY KEY,
    name VARCHAR(255)
);

--changeset cefeis:3fbe8ea9-c640-4ea7-afb5-8d51eafae226
CREATE TABLE books_authors
(
    book_id BIGINT REFERENCES books(id),
    author_id BIGINT REFERENCES authors(id),
    PRIMARY KEY (book_id, author_id)
);

--changeset cefeis:f6c975ef-bd52-4d69-8e59-d705bf118e22
CREATE TABLE books_genres
(
    book_id BIGINT REFERENCES books(id),
    genre_id BIGINT REFERENCES genres(id),
    PRIMARY KEY (book_id, genre_id)
)