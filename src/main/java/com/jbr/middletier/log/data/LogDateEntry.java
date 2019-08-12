package com.jbr.middletier.log.data;

/**
 * Created by jason on 18/01/17.
 */
public class LogDateEntry {
    private final String id;
    private final String name;

    public LogDateEntry(Long id, String name) {
        this.id = id.toString();
        this.name = name;
    }

    public String getId() { return this.id; }

    public String getName() { return this.name; }
}
