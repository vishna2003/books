package com.sismics.books.core.dao.jpa.dto;

import java.util.List;

/**
 * Data Transfer Object for CommonLibraryBook.
 */
public class CommonLibraryBookDto {
    private Integer id;
    private String title;
    private List<String> authors;
    private List<String> genres;
    private Double rating;
    private String thumbnailUrl;

    // Constructors, Getters, and Setters

    public CommonLibraryBookDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}