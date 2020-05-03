package com.jbr.middletier.log.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name="logging_event")
@SuppressWarnings({"unused", "FieldCanBeLocal", "SameParameterValue"})
public class LoggingEvent {
    @Id
    @Column(name="event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @Column(name="timestmp")
    private long timeStamp;

    @Column(name="formatted_message")
    private String formattedMessage;

    @Column(name="logger_name")
    private String loggerName;

    @Column(name="level_string")
    private String levelString;

    @Column(name="thread_name")
    private String threadName;

    @Column(name="reference_flag")
    private String referenceFlag;

    @Column(name="arg0")
    private String arg0;

    @Column(name="arg1")
    private String arg1;

    @Column(name="arg2")
    private String arg2;

    @Column(name="arg3")
    private String arg3;

    @Column(name="caller_filename")
    private String callerFilename;

    @Column(name="caller_class")
    private String callerClass;

    @Column(name="caller_method")
    private String callerMethod;

    @Column(name="caller_line")
    private String callerLine;


    public long getDate() {
        // Convert the timestamp to a date numeric.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        return calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getFormattedMessage() {
        return this.formattedMessage;
    }

    public String getTypeId() { return callerClass; }

    public java.sql.Time getTime() {
        // Convert the timestamp to a date numeric.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return java.sql.Time.valueOf(sdf.format(calendar.getTime()));
    }

    public String getLevelString() { return this.levelString; }

    public int getEventId() { return (int)eventId; }

    public String getTimeStampString() { return Long.toString(this.timeStamp); }

    public String getCallerClass() { return this.callerClass; }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
    }

    void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    void setLevelString(String levelString) {
        this.levelString = levelString;
    }

    void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    void setCallerFilename(String callerFilename) {
        this.callerFilename = callerFilename;
    }

    void setCallerClass(String callerClass) {
        this.callerClass = callerClass;
    }

    void setCallerMethod(String callerMethod) {
        this.callerMethod = callerMethod;
    }

    void setCallerLine(String callerLine) {
        this.callerLine = callerLine;
    }
}
