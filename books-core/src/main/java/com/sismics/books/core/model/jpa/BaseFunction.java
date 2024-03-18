package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Base function entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_BASE_FUNCTION")
@Data
public class BaseFunction {
    /**
     * Base function ID (ex: "ADMIN").
     */
    @Id
    @Column(name = "BAF_ID_C", length = 10)
    private String id;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
