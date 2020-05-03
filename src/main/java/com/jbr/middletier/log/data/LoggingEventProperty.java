package com.jbr.middletier.log.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="logging_event_property")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class LoggingEventProperty {
    @Id
    @Column(name="event_id")
    private long eventId;

    @Column(name="mapped_key")
    private String mappedKey;

    @Column(name="mapped_value")
    private String text;

    LoggingEventProperty(long eventId, String mappedKey, String text) {
        this.eventId = eventId;
        this.mappedKey = mappedKey;
        this.text = text;
    }
}
