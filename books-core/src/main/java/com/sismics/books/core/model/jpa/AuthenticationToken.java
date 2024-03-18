package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Authentication token entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_AUTHENTICATION_TOKEN")
@Data
public class AuthenticationToken {
    /**
     * Token.
     */
    @Id
    @Column(name = "AUT_ID_C", length = 36)
    private String id;

    /**
     * User ID.
     */
    @Column(name = "AUT_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Remember the user next time (long lasted session).
     */
    @Column(name = "AUT_LONGLASTED_B", nullable = false)
    private boolean longLasted;
    
    /**
     * Token creation date.
     */
    @Column(name = "AUT_CREATIONDATE_D", nullable = false)
    private Date creationDate;

    /**
     * Last connection date using this token.
     */
    @Column(name = "AUT_LASTCONNECTIONDATE_D")
    private Date lastConnectionDate;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", "**hidden**")
                .add("userId", userId)
                .add("longLasted", longLasted)
                .toString();
    }
}
