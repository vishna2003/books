package com.sismics.books.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.common.base.Objects;
import lombok.Data;

/**
 * Connected application entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_APP")
@Data
public class App {
    /**
     * Connected application ID.
     */
    @Id
    @Column(name = "APP_ID_C", length = 20)
    private String id;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
