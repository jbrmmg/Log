package com.jbr.middletier.log.data;

/**
 * Created by jason on 20/01/17.
 */
@SuppressWarnings("unused")
public class ExternalLogTypeEntry {
    private final String id;
    private final String name;
    private final String icon;

    ExternalLogTypeEntry(LogTypeEntry logTypeEntry) {
        this.id = logTypeEntry.getId();
        this.name = logTypeEntry.getName();
        this.icon = logTypeEntry.getImage();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() { return icon; }
}
