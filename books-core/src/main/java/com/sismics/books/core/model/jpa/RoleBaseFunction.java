package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Role base function.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_ROLE_BASE_FUNCTION")
@Data
public class RoleBaseFunction {
    /**
     * Role base function ID.
     */
    @Id
    @Column(name = "RBF_ID_C", length = 36)
    private String id;
    
    /**
     * Role ID.
     */
    @Column(name = "RBF_IDROLE_C", nullable = false, length = 36)
    private String roleId;
    
    /**
     * Base function ID.
     */
    @Column(name = "RBF_IDBASEFUNCTION_C", nullable = false, length = 36)
    private String baseFunctionId;
    
    /**
     * Creation date.
     */
    @Column(name = "RBF_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "RBF_DELETEDATE_D")
    private Date deleteDate;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("userId", roleId)
                .add("baseFunctionId", baseFunctionId)
                .toString();
    }
}
