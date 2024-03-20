package com.sismics.books.core.model.jpa;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "T_COMMON_LIBRARY_BOOK")
public class CommonLibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "clb_title_c")
    private String title;

    @Column(name = "clb_authors_c")
    private String authors; // Comma-separated list

    @Column(name = "clb_genres_c")
    private String genres; // Comma-separated list

    @Column(name = "clb_rating_n")
    private Double rating;

    @Column(name = "clb_thumbnail_url_c")
    private String thumbnailUrl;

    // Constructors
    public CommonLibraryBook() {
    }

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

    // Additional methods like equals(), hashCode(), and toString() can be added as needed
}