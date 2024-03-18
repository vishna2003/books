package com.sismics.books.core.model.jpa;

// import java.util.Objects; // Added by us for code optimization
import com.google.common.base.Objects; // Present originally

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;


/**
 * Link between a book and a tag.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_USER_BOOK_TAG")
@Data
public class UserBookTag implements Serializable {
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
     * User book ID.
     */
    @Id
    @Column(name = "BOT_IDUSERBOOK_C", length = 36)
    private String userBookId;
    
    /**
     * Tag ID.
     */
    @Id
    @Column(name = "BOT_IDTAG_C", length = 36)
    private String tagId;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userBookId == null) ? 0 : userBookId.hashCode());
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
       UserBookTag other = (UserBookTag) obj;
       if (userBookId == null) {
           if (other.userBookId != null) {
               return false;
           }
       } else if (!userBookId.equals(other.userBookId)) {
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

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj) {
    //         return true;
    //     }
    //     if (obj == null || getClass() != obj.getClass()) {
    //         return false;
    //     }
    //     UserBookTag other = (UserBookTag) obj;
    //     return Objects.equals(userBookId, other.userBookId) &&
    //             Objects.equals(tagId, other.tagId);
    // }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userBookId", userBookId)
                .add("tagId", tagId)
                .toString();
    }
}
