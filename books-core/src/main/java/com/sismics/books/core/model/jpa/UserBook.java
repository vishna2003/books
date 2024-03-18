package com.sismics.books.core.model.jpa;

import java.io.Serializable;
import java.util.Date;
// import java.util.Objects; // Added by us for code optimization

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects; // Present originally
import lombok.Data;

/**
 * User book entity.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_USER_BOOK")
@Data
public class UserBook implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * User book ID.
     */
    @Id
    @Column(name = "UBK_ID_C", length = 36)
    private String id;
    
    /**
     * Book ID.
     */
    @Id
    @Column(name = "UBK_IDBOOK_C", nullable = false, length = 36)
    private String bookId;
    
    /**
     * User ID.
     */
    @Id
    @Column(name = "UBK_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Creation date.
     */
    @Column(name = "UBK_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "UBK_DELETEDATE_D")
    private Date deleteDate;
    
    /**
     * Read date.
     */
    @Column(name = "UBK_READDATE_D")
    private Date readDate;
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
       UserBook other = (UserBook) obj;
       if (bookId == null) {
           if (other.bookId != null) {
               return false;
           }
       } else if (!bookId.equals(other.bookId)) {
           return false;
       }
       if (userId == null) {
           if (other.userId != null) {
               return false;
           }
       } else if (!userId.equals(other.userId)) {
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
    //     UserBook other = (UserBook) obj;
    //     return Objects.equals(bookId, other.bookId) &&
    //             Objects.equals(userId, other.userId);
    // }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
