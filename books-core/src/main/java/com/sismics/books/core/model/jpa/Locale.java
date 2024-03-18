package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Locale entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_LOCALE")
@Data
public class Locale {
    /**
     * Locale ID (ex: fr_FR).
     */
    @Id
    @Column(name = "LOC_ID_C", length = 10)
    private String id;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
