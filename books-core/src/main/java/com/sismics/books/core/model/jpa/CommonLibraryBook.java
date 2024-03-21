package com.sismics.books.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_COMMON_LIBRARY_BOOK")
public class CommonLibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLB_ID_C")
    private String id;

    @Column(name = "CLB_TITLE_C")
    private String title;

    @Column(name = "CLB_AUTHORS_C")
    private String authors;

    @Column(name = "CLB_GENRES_C")
    private String genres;

    @Column(name = "CLB_TOTAL_RATINGS")
    private Integer totalRatings;

    @Column(name = "CLB_AVERAGE_RATING")
    private Double averageRating;

    // Constructors
    // public CommonLibraryBook() {
    // }

    // Getters and Setters
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

    // Additional methods like equals(), hashCode(), and toString() can be added as needed
}
