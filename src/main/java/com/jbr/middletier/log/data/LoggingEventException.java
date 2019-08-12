package com.jbr.middletier.log.data;

import javax.persistence.*;

@Entity
@Table(name="logging_event_exception")
@IdClass(LoggingEventExceptionId.class)
public class LoggingEventException {
    @Id
    @Column(name="event_id")
    private long eventId;

    @Id
    @Column(name="i")
    private int i;

    @Column(name="trace_line")
    private String traceLine;
}
