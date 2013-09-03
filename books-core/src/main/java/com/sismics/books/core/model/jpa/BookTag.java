package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Link between a book and a tag.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_BOOK_TAG")
public class BookTag implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Book tag ID.
     */
    @Id
    @Column(name = "BOT_ID_C", length = 36)
    private String id;
    
    /**
     * Book ID.
     */
    @Id
    @Column(name = "BOT_IDBOOK_C", length = 36)
    private String bookId;
    
    /**
     * Tag ID.
     */
    @Id
    @Column(name = "BOT_IDTAG_C", length = 36)
    private String tagId;

    /**
     * Getter of id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Getter de bookId.
     *
     * @return the bookId
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Setter de bookId.
     *
     * @param documentId bookId
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * Getter de tagId.
     *
     * @return the tagId
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * Setter de tagId.
     *
     * @param tagId tagId
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BookTag other = (BookTag) obj;
        if (bookId == null) {
            if (other.bookId != null) {
                return false;
            }
        } else if (!bookId.equals(other.bookId)) {
            return false;
        }
        if (tagId == null) {
            if (other.tagId != null) {
                return false;
            }
        } else if (!tagId.equals(other.tagId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("bookId", bookId)
                .add("tagId", tagId)
                .toString();
    }
}
