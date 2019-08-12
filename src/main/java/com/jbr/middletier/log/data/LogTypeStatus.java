package com.jbr.middletier.log.data;

/**
 * Created by jason on 15/01/17.
 */
class LogTypeStatus {
    private final String id;
    private final LogTypeEntry logTypeEntry;
    private final String typeClass;
    private boolean active;

    public LogTypeStatus ( LogTypeEntry logTypeEntry ) {
        this.id = logTypeEntry.getId();
        this.logTypeEntry = logTypeEntry;
        this.typeClass = logTypeEntry.getLogClass();
        this.active = false;
    }

    public String getId() {
        return this.id;
    }

    public String getTypeClass() { return this.typeClass; }

    public boolean isActive() {
        return this.active;
    }

    public LogTypeEntry logTypeEntry() {
        return this.logTypeEntry;
    }

    public void setActive() {
        this.active = true;
    }
}
