package com.jbr.middletier.log.control;

/**
 * Created by jason on 15/01/17.
 */
public class PublishedLog {
    @SuppressWarnings("CanBeFinal")
    private String type;
    @SuppressWarnings("CanBeFinal")
    private String level;
    @SuppressWarnings("CanBeFinal")
    private String text;

    public String getType() { return this.type; }

    public String getLevel() { return this.level; }

    public String getText() { return this.text; }
}

