package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;
import lombok.Data;

/**
 * Book entity.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_BOOK")
@Data
public class Book {
    /**
     * Book ID.
     */
    @Id
    @Column(name = "BOK_ID_C", length = 36)
    private String id;
    
    /**
     * Title.
     */
    @Column(name = "BOK_TITLE_C", nullable = false, length = 255)
    private String title;
    
    /**
     * Subtitle.
     */
    @Column(name = "BOK_SUBTITLE_C", length = 255)
    private String subtitle;
    
    /**
     * Author.
     */
    @Column(name = "BOK_AUTHOR_C", nullable = false, length = 255)
    private String author;
    
    /**
     * Description.
     */
    @Column(name = "BOK_DESCRIPTION_C", length = 4000)
    private String description;
    
    /**
     * ISBN 10.
     */
    @Column(name = "BOK_ISBN10_C", length = 10)
    private String isbn10;
    
    /**
     * ISBN 13.
     */
    @Column(name = "BOK_ISBN13_C", length = 13)
    private String isbn13;
    
    /**
     * Page count.
     */
    @Column(name = "BOK_PAGECOUNT_N")
    private Long pageCount;
    
    /**
     * Language (ISO 639-1).
     */
    @Column(name = "BOK_LANGUAGE_C", length = 2)
    private String language;
    
    /**
     * Publication date.
     */
    @Column(name = "BOK_PUBLISHDATE_D", nullable = false)
    private Date publishDate;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .toString();
    }
}
