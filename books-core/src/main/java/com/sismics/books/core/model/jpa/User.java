package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * User entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_USER")
@Data
public class User {
    /**
     * User ID.
     */
    @Id
    @Column(name = "USE_ID_C", length = 36)
    private String id;
    
    /**
     * Locale ID.
     */
    @Column(name = "USE_IDLOCALE_C", nullable = false, length = 10)
    private String localeId;
    
    /**
     * Role ID.
     */
    @Column(name = "USE_IDROLE_C", nullable = false, length = 36)
    private String roleId;
    
    /**
     * User's username.
     */
    @Column(name = "USE_USERNAME_C", nullable = false, length = 50)
    private String username;
    
    /**
     * User's password.
     */
    @Column(name = "USE_PASSWORD_C", nullable = false, length = 100)
    private String password;

    /**
     * Email address.
     */
    @Column(name = "USE_EMAIL_C", nullable = false, length = 100)
    private String email;
    
    /**
     * Theme.
     */
    @Column(name = "USE_THEME_C", nullable = false, length = 100)
    private String theme;
    
    /**
     * True if the user hasn't dismissed the first connection screen.
     */
    @Column(name = "USE_FIRSTCONNECTION_B", nullable = false)
    private boolean firstConnection;

    /**
     * Creation date.
     */
    @Column(name = "USE_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "USE_DELETEDATE_D")
    private Date deleteDate;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .toString();
    }
}
