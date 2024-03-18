package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;
import lombok.Data;


/**
 * User's contact entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_USER_CONTACT")
@Data
public class UserContact {
    /**
     * Contact ID.
     */
    @Id
    @Column(name = "USC_ID_C", length = 36)
    private String id;
    
    /**
     * User ID.
     */
    @Column(name = "USC_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Connected application ID.
     */
    @Column(name = "USC_IDAPP_C", nullable = false, length = 20)
    private String appId;
    
    /**
     * Contact ID in the connected application.
     */
    @Column(name = "USC_EXTERNALID_C", nullable = false, length = 50)
    private String externalId;
    
    /**
     * Contact's fullname in the connected application.
     */
    @Column(name = "USC_FULLNAME_C", length = 100)
    private String fullName;
    
    /**
     * Contact's email in the connected application.
     */
    @Column(name = "USC_EMAIL_C", length = 100)
    private String email;
    
    /**
     * Creation date.
     */
    @Column(name = "USC_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Update date.
     */
    @Column(name = "USC_UPDATEDATE_D")
    private Date updateDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "USC_DELETEDATE_D")
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
