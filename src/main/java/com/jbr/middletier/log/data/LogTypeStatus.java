package com.jbr.middletier.log.data;

/**
 * Created by jason on 15/01/17.
 */
class LogTypeStatus {
    private final String id;
    private final LogTypeEntry logTypeEntry;
    private final String typeClass;
    private boolean active;

    LogTypeStatus(LogTypeEntry logTypeEntry) {
        this.id = logTypeEntry.getId();
        this.logTypeEntry = logTypeEntry;
        this.typeClass = logTypeEntry.getLogClass();
        this.active = false;
    }

    public String getId() {
        return this.id;
    }

    String getTypeClass() { return this.typeClass; }

    boolean isActive() {
        return this.active;
    }

    LogTypeEntry logTypeEntry() {
        return this.logTypeEntry;
    }

    void setActive() {
        this.active = true;
    }
}
