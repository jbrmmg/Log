package com.jbr.middletier.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="log",ignoreUnknownFields = true)
public class ApplicationProperties {
    private String serviceName;

    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getServiceName() { return this.serviceName; }
}
