package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Role (set of base functions).
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_ROLE")
@Data
public class Role {
    /**
     * Role ID.
     */
    @Id
    @Column(name = "ROL_ID_C", length = 36)
    private String id;
    
    /**
     * Role name.
     */
    @Column(name = "ROL_NAME_C", nullable = false, length = 50)
    private String name;
    
    /**
     * Creation date.
     */
    @Column(name = "ROL_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "ROL_DELETEDATE_D")
    private Date deleteDate;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
