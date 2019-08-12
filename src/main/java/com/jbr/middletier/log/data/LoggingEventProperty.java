package com.jbr.middletier.log.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="logging_event_property")
public class LoggingEventProperty {
    @Id
    @Column(name="event_id")
    private long eventId;

    @Column(name="mapped_key")
    private String mappedKey;

    @Column(name="mapped_value")
    private String text;
}
