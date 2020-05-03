package com.jbr.middletier.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="log")
public class ApplicationProperties {
    private String serviceName;
    private String calendar;

    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getServiceName() { return this.serviceName; }

    public void setCalendar(String calendar) { this.calendar = calendar; }

    public String getCalendar() { return this.calendar; }
}
