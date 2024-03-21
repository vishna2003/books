package com.sismics.books.core.service;

import com.sismics.books.core.dao.jpa.CommonLibraryBookDao;
import com.sismics.books.core.dao.jpa.dto.CommonLibraryBookDto;
import com.sismics.books.core.model.jpa.CommonLibraryBook;

import java.util.ArrayList;
import java.util.List;

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
        book.setTotalRatings(bookDto.getTotalRatings());
        book.setAverageRating(bookDto.getAverageRating());
        System.out.println("1");
        return commonLibraryBookDao.create(book);
    }

    /**
     * Updates the rating of a book. The rating must be between 1 and 10.
     * 
     * @param bookId ID of the book
     * @param rating New rating to be set
     */
    // public void rateBook(String bookId, Double rating) {
    //     if (rating < 1 || rating > 10) {
    //         throw new IllegalArgumentException("Rating must be between 1 and 10.");
    //     }
    //     commonLibraryBookDao.updateRating(bookId, rating);
    // }

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
        dto.setTotalRatings(book.getTotalRatings());
        dto.setAverageRating(book.getAverageRating());
        return dto;
    }



    public static void main(String[] args) {
        // Instantiate the service
        CommonLibraryBookService bookService = new CommonLibraryBookService();
        
        // Create a book DTO with sample data
        CommonLibraryBookDto bookDto = new CommonLibraryBookDto();
        bookDto.setTitle("Sample Book");
        bookDto.setAuthors("Author A, Author B");
        bookDto.setGenres("Fiction, Mystery");
        bookDto.setTotalRatings(1); // Set total ratings to 1
        bookDto.setAverageRating(8.5); // Set average rating
        
        // Add the book to the common library
        String bookId = bookService.addBook(bookDto);
        
        // Check if the book was added successfully
        if (bookId != null) {
            System.out.println("Book added successfully with ID: " + bookId);
        } else {
            System.out.println("Failed to add book.");
        }
    }}