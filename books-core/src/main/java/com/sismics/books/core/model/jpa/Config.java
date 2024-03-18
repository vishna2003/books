package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;
import com.sismics.books.core.constant.ConfigType;
import lombok.Data;

import javax.persistence.*;

/**
 * Configuration parameter entity.
 * 
 * @author jtremeaux
 */
@Entity
@Table(name = "T_CONFIG")
@Data
public class Config {
    /**
     * Configuration parameter ID.
     */
    @Id
    @Column(name = "CFG_ID_C", length = 50)
    @Enumerated(EnumType.STRING)
    private ConfigType id;
    
    /**
     * Configuration parameter value.
     */
    @Column(name = "CFG_VALUE_C", length = 250)
    private String value;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
