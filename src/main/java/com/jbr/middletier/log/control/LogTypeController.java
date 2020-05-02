package com.jbr.middletier.log.control;

import com.jbr.middletier.log.data.ExternalLogTypeEntry;
import com.jbr.middletier.log.data.LogTypeEntry;
import com.jbr.middletier.log.data.LogTypeManager;
import com.jbr.middletier.log.dataaccess.LogTypeEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * Created by jason on 31/05/16.
 *
 *  curl -X GET -H "Content-type: application/json" http://localhost:13001/jbr/ext/log/type
 */

@Controller
@RequestMapping("/jbr")
public class LogTypeController {
    final static private Logger LOG = LoggerFactory.getLogger(LogTypeController.class);

    private final
    LogTypeManager logTypeManager;

    private final LogTypeEntryRepository logTypeEntryRepository;

    @Autowired
    public LogTypeController(LogTypeManager logTypeManager,
                             LogTypeEntryRepository logTypeEntryRepository) {
        this.logTypeManager = logTypeManager;
        this.logTypeEntryRepository = logTypeEntryRepository;
    }

    @RequestMapping(path= "/ext/log/type", method= RequestMethod.GET)
    public @ResponseBody
    Iterable<ExternalLogTypeEntry> getLogTypes() {
        LOG.info("Request for log types.");
        return logTypeManager.getLogTypes();
    }

    @RequestMapping(path= "/int/log/type", method= RequestMethod.POST)
    public @ResponseBody
    LogTypeEntry addLogType(@RequestBody LogTypeEntry logTypeEntry) throws Exception {
        LOG.info("Create a log type.");

        // Does this entry already exist?
        Optional<LogTypeEntry> current = logTypeEntryRepository.findById(logTypeEntry.getId());
        if(current.isPresent()) {
            throw new Exception(logTypeEntry.getId() + " already exists");
        }

        this.logTypeEntryRepository.save(logTypeEntry);

        return logTypeEntry;
    }

    @RequestMapping(path= "/int/log/type", method= RequestMethod.PUT)
    public @ResponseBody
    LogTypeEntry updateLogType(@RequestBody LogTypeEntry logTypeEntry) throws Exception {
        LOG.info("Create a log type.");

        // Does this entry already exist?
        Optional<LogTypeEntry> current = logTypeEntryRepository.findById(logTypeEntry.getId());
        if(!current.isPresent()) {
            throw new Exception(logTypeEntry.getId() + " does not exist");
        }

        this.logTypeEntryRepository.save(logTypeEntry);

        return logTypeEntry;
    }

    @RequestMapping(path= "/int/log/type", method= RequestMethod.DELETE)
    public @ResponseBody
    LogTypeEntry deleteLogType(@RequestBody LogTypeEntry logTypeEntry) throws Exception {
        LOG.info("Create a log type.");

        // Does this entry already exist?
        Optional<LogTypeEntry> current = logTypeEntryRepository.findById(logTypeEntry.getId());
        if(!current.isPresent()) {
            throw new Exception(logTypeEntry.getId() + " does not exist");
        }

        this.logTypeEntryRepository.delete(logTypeEntry);

        return logTypeEntry;
    }
}
