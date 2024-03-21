package com.sismics.books.core.dao.jpa.dto;

/**
 * Data Transfer Object for CommonLibraryBook.
 */
public class CommonLibraryBookDto {
    private String id;
    private String title;
    private String authors;
    private String genres;
    private Integer totalRatings;
    private Double averageRating;

    // Constructors, Getters, and Setters

    public CommonLibraryBookDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}