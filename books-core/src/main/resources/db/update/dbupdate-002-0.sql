-- Assuming the previous update was dbupdate-001-0.sql, this file would be named dbupdate-002-0.sql
SET IGNORECASE TRUE;

CREATE TABLE common_library_book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    rating DOUBLE,
    thumbnail_url VARCHAR(2048)
);

CREATE TABLE common_library_book_authors (
    book_id BIGINT,
    author VARCHAR(255),
    FOREIGN KEY (book_id) REFERENCES common_library_book(id)
);

CREATE TABLE common_library_book_genres (
    book_id BIGINT,
    genre VARCHAR(255),
    FOREIGN KEY (book_id) REFERENCES common_library_book(id)
);

CREATE TABLE common_library_book_ratings (
    book_id BIGINT,
    rating INT,
    FOREIGN KEY (book_id) REFERENCES common_library_book(id)
);