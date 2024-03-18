package com.sismics.books.core.service;

import com.sismics.books.core.model.jpa.Book;

public interface BookSearchService {
    Book searchBook(String isbn) throws Exception;
}