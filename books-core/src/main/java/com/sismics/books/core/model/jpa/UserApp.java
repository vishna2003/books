package com.sismics.books.core.model.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Link between a user and a connected application.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_USER_APP")
@Data
@NoArgsConstructor
public class UserApp {
    /**
     * User app ID.
     */
    @Id
    @Column(name = "USA_ID_C", length = 36)
    private String id;
    
    /**
     * User ID.
     */
    @Column(name = "USA_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Connected application ID.
     */
    @Column(name = "USA_IDAPP_C", nullable = false, length = 20)
    private String appId;
    
    /**
     * OAuth access token.
     */
    @Column(name = "USA_ACCESSTOKEN_C", length = 255)
    private String accessToken;
    
    /**
     * User's username in the application.
     */
    @Column(name = "USA_USERNAME_C", length = 100)
    private String username;
    
    /**
     * User's ID in the application.
     */
    @Column(name = "USA_EXTERNALID_C", length = 50)
    private String externalId;
    
    /**
     * User's email in the application.
     */
    @Column(name = "USA_EMAIL_C", length = 100)
    private String email;
    
    /**
     * Share on this application.
     */
    @Column(name = "USA_SHARING_B", nullable = false)
    private boolean sharing;
    
    /**
     * Creation date.
     */
    @Column(name = "USA_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "USA_DELETEDATE_D")
    private Date deleteDate;
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("appId", appId)
                .toString();
    }
}
