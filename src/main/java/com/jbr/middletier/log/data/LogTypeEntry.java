package com.jbr.middletier.log.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jason on 07/12/16.
 */

@Entity
@Table(name="log_type")
public class LogTypeEntry {
    @Id
    @Column(name="log_type_id")
    private String id;

    @Column(name="log_name")
    private String logTypeName;

    @Column(name="log_image")
    private String logImage;

    @Column(name="log_class")
    private String logClass;

    protected LogTypeEntry() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return logTypeName;
    }

    public String getImage() { return logImage;}

    public String getLogClass() { return logClass; }
}
