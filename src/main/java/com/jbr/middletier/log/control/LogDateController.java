package com.jbr.middletier.log.control;

import com.jbr.middletier.log.data.LogDateEntry;
import com.jbr.middletier.log.data.LogTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jason on 18/01/17.
 */

@Controller
@RequestMapping("/jbr/ext/log/date")
public class LogDateController {
    final static private Logger LOG = LoggerFactory.getLogger(LogDateController.class);

    private final
    LogTypeManager logTypeManager;

    @Autowired
    public LogDateController(LogTypeManager logTypeManager) {
        this.logTypeManager = logTypeManager;
    }

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    Iterable<LogDateEntry> getLogDates() {
        LOG.info("Request for log types.");
        return logTypeManager.getLogDates();
    }
}
