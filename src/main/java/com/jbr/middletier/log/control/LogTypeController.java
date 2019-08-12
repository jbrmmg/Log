package com.jbr.middletier.log.control;

import com.jbr.middletier.log.data.ExternalLogTypeEntry;
import com.jbr.middletier.log.data.LogTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jason on 31/05/16.
 *
 *  curl -X GET -H "Content-type: application/json" http://localhost:13001/jbr/ext/log/type
 */

@Controller
@RequestMapping("/jbr/ext/log/type")
public class LogTypeController {
    final static private Logger LOG = LoggerFactory.getLogger(LogTypeController.class);

    private final
    LogTypeManager logTypeManager;

    @Autowired
    public LogTypeController(LogTypeManager logTypeManager) {
        this.logTypeManager = logTypeManager;
    }

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    Iterable<ExternalLogTypeEntry> getLogTypes() {
        LOG.info("Request for log types.");
        return logTypeManager.getLogTypes();
    }
}
