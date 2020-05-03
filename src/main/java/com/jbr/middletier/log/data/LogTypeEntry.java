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
@SuppressWarnings({"unused", "FieldCanBeLocal", "SameParameterValue"})
public class LogTypeEntry {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String logTypeName;

    @Column(name="image")
    private String logImage;

    @Column(name="class")
    private String logClass;

    public LogTypeEntry() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return logTypeName;
    }

    String getImage() { return logImage;}

    String getLogClass() { return logClass; }

    public void setId(String id) { this.id = id; }

    public void setName(String name) { this.logTypeName = name; }

    public void setImage(String logImage) { this.logImage = logImage; }

    public void setLogClass(String logClass) { this.logClass = logClass; }
}
