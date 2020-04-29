package com.jbr.middletier.log.health;

import com.jbr.middletier.log.config.ApplicationProperties;
import com.jbr.middletier.log.data.LogTypeEntry;
import com.jbr.middletier.log.dataaccess.LogTypeEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jason on 23/04/17.
 */

@Component
public class ServiceHealthIndicator implements HealthIndicator {
    final static private Logger LOG = LoggerFactory.getLogger(ServiceHealthIndicator.class);

    private final
    LogTypeEntryRepository logTypeEntryRepository;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public ServiceHealthIndicator(
            LogTypeEntryRepository logTypeEntryRepository,
            ApplicationProperties applicationProperties ) {
        this.logTypeEntryRepository = logTypeEntryRepository;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public Health health() {
        try {
            List<LogTypeEntry> logTypeList = (List<LogTypeEntry>) logTypeEntryRepository.findAll();
            LOG.info(String.format("Check Database %s.", logTypeList.size()));

            return Health.up().withDetail("service", applicationProperties.getServiceName()).withDetail("Log Types",logTypeList.size()).build();
        } catch (Exception e) {
            LOG.error("Failed to check health",e);
        }

        return Health.down().withDetail("service", applicationProperties.getServiceName()).build();
    }
}
