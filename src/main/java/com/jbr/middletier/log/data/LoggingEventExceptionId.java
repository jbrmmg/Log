package com.jbr.middletier.log.data;

import java.io.Serializable;
import java.util.Objects;

public class LoggingEventExceptionId  implements Serializable {
    private long eventId;
    private int  i;

    public LoggingEventExceptionId(long eventId, int i) {
        this.eventId = eventId;
        this.i = i;
    }

    public LoggingEventExceptionId()
    {
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof LoggingEventExceptionId)) {
            return false;
        }

        LoggingEventExceptionId statementId = (LoggingEventExceptionId)o;

        return Objects.equals(statementId.eventId, this.eventId) && Objects.equals(statementId.i, this.i);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + i;
        result = 31 * result + (int)eventId;
        return result;
    }
}
