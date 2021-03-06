package com.jbr.middletier.log.control;

import com.jbr.middletier.log.data.LogTypeManager;
import com.jbr.middletier.log.data.LoggingEvent;
import com.jbr.middletier.log.dataaccess.LoggingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsBetween;
import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsLikeClass;

/**
 * Get log entries from the log database.
 *
 * curl -X GET -H "Content-type: application/json" http://localhost:13001/jbr/ext/log?date=20161225&amp;type=CKNET
 *
 * @author Jason
 * @since 31/05/2016
 */

@Controller
@RequestMapping("/jbr")
public class LogEntryController {
    final static private Logger LOG = LoggerFactory.getLogger(LogEntryController.class);

    private final LoggingEventRepository loggingEventRepository;

    private final LogTypeManager logTypeManager;

    @Autowired
    public LogEntryController(LoggingEventRepository loggingEventRepository,
                              LogTypeManager logTypeManager) {
        this.loggingEventRepository = loggingEventRepository;
        this.logTypeManager = logTypeManager;
    }

    /*
     * Returns the log entries for the
     *
     * @param date The date in the format yyyymmdd
     * @param type The type of log being queried.
     */
    @RequestMapping(path="/ext/log/data", method= RequestMethod.GET)
    public @ResponseBody
    List getLogData(@RequestParam(value="date", defaultValue="00000000") long date,
                                       @RequestParam(value="type", defaultValue="UNKN") String type) {
        // Convert the type specified to the class.
        String typeClass = logTypeManager.getClassForType(type);

        // Calculate the start and end timestamp.
        int year = (int)(date / 10000);
        date = date - year * 10000;

        int month = (int)(date / 100);

        int day = (int)(date - month * 100);

        Calendar calendar = Calendar.getInstance();
        //noinspection MagicConstant
        calendar.set(year, month - 1, day, 0, 0, 0);

        long from = calendar.getTimeInMillis();
        from /= 1000;
        from *= 1000;

        calendar.add(Calendar.DAY_OF_YEAR,1);
        long to = calendar.getTimeInMillis();
        to /= 1000;
        to *= 1000;
        to -= 1;


        LOG.info("Request for log {} from {} to {}.", type, from, to);
        //noinspection unchecked
        return loggingEventRepository.findAll(Specification.where(logIsLikeClass(typeClass)).and(logIsBetween(from,to)), new Sort(Sort.Direction.ASC, "timeStamp"));

    }

    @RequestMapping(path="/int/log/data", method= RequestMethod.POST)
    public @ResponseBody LoggingEvent saveLogEntry ( @RequestBody LoggingEvent log ) {
        LOG.info("Log Entry: " + log.getLevelString() + ":" + log.getFormattedMessage());

        // Find the log type for the given class
        String type = logTypeManager.getTypeForClass(log.getCallerClass());
        if(type == null) {
            return null;
        }

        // Set the time stamp.
        log.setTimeStamp(logTypeManager.getTimeStampNow());

        return this.loggingEventRepository.save(log);
    }
}
