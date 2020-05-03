package com.jbr.middletier.log.data;

import javax.persistence.*;

@Entity
@Table(name="logging_event_exception")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class LoggingEventException {
    @EmbeddedId
    private LoggingEventExceptionId id;

    @Column(name="trace_line")
    private String traceLine;

    LoggingEventException(LoggingEventExceptionId id, String traceLine) {
        this.id = id;
        this.traceLine = traceLine;
    }
}
