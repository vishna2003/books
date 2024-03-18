package com.sismics.books.core.service;

import com.sismics.books.core.dao.jpa.CommonLibraryBookDao;
import com.sismics.books.core.dao.jpa.dto.CommonLibraryBookDto;
import com.sismics.books.core.model.jpa.CommonLibraryBook;
import com.sismics.util.context.ThreadLocalContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

public class CommonLibraryBookService {

    private CommonLibraryBookDao commonLibraryBookDao = new CommonLibraryBookDao();

    /**
     * Adds a book to the common library.
     * 
     * @param bookDto Data transfer object containing book details
     * @return ID of the newly added book
     */
    public String addBook(CommonLibraryBookDto bookDto) {
        CommonLibraryBook book = new CommonLibraryBook();
        book.setTitle(bookDto.getTitle());
        book.setAuthors(bookDto.getAuthors());
        book.setGenres(bookDto.getGenres());
        book.setRating(bookDto.getRating());
        book.setThumbnailUrl(bookDto.getThumbnailUrl());
        return commonLibraryBookDao.create(book);
    }

    /**
     * Updates the rating of a book. The rating must be between 1 and 10.
     * 
     * @param bookId ID of the book
     * @param rating New rating to be set
     */
    public void rateBook(String bookId, Double rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10.");
        }
        commonLibraryBookDao.updateRating(bookId, rating);
    }

    /**
     * Fetches all books in the common library.
     * 
     * @return List of book DTOs
     */
    public List<CommonLibraryBookDto> viewBooks() {
        List<CommonLibraryBook> books = commonLibraryBookDao.findAll();
        List<CommonLibraryBookDto> dtos = new ArrayList<>();
        for (CommonLibraryBook book : books) {
            dtos.add(convertToDto(book));
        }
        return dtos;
    }
    
    // Helper method to convert CommonLibraryBook entity to CommonLibraryBookDto
    private CommonLibraryBookDto convertToDto(CommonLibraryBook book) {
        CommonLibraryBookDto dto = new CommonLibraryBookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setGenres(book.getGenres());
        dto.setRating(book.getRating());
        dto.setThumbnailUrl(book.getThumbnailUrl());
        return dto;
    }

}