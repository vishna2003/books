package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Tag.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_TAG")
@Data
public class Tag {
    /**
     * Tag ID.
     */
    @Id
    @Column(name = "TAG_ID_C", length = 36)
    private String id;
    
    /**
     * Tag name.
     */
    @Column(name = "TAG_NAME_C", nullable = false, length = 36)
    private String name;
    
    /**
     * User ID.
     */
    @Column(name = "TAG_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Creation date.
     */
    @Column(name = "TAG_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "TAG_DELETEDATE_D")
    private Date deleteDate;
    
    /**
     * Tag name.
     */
    @Column(name = "TAG_COLOR_C", nullable = false, length = 7)
    private String color;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
