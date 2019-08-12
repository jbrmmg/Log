package com.jbr.middletier.log.schedule;

import com.jbr.middletier.log.data.LogTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jason on 17/01/17.
 */

@Component
public class Rollover {
    final static private Logger LOG = LoggerFactory.getLogger(Rollover.class);

    private final
    LogTypeManager logTypeManager;

    @Autowired
    public Rollover(LogTypeManager logTypeManager) {
        this.logTypeManager = logTypeManager;
    }

    @Scheduled(cron = "${middle.tier.log.db.schedule.rollover}")
    public void rolloverDate() {
        LOG.info("Date rolled.");
        logTypeManager.rolloverDate();
    }
}
