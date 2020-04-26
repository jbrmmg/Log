package com.jbr.middletier.log.data;

import com.jbr.middletier.log.config.ApplicationProperties;
import com.jbr.middletier.log.dataaccess.LogTypeEntryRepository;
import com.jbr.middletier.log.dataaccess.LoggingEventExceptionRepository;
import com.jbr.middletier.log.dataaccess.LoggingEventPropertyRepository;
import com.jbr.middletier.log.dataaccess.LoggingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsLikeClass;

/**
 * Manages the logs - keeps track of the sequence number for each log and cleans up old log entries.
 */

@Component
public class LogTypeManager {
    final static private Logger LOG = LoggerFactory.getLogger(LogTypeManager.class);

    final static private int MAX_LOG_DAYS = 5;

    private final LogTypeEntryRepository            logTypeEntryRepository;
    private final LoggingEventRepository            loggingEventRepository;
    private final LoggingEventExceptionRepository   loggingEventExceptionRepository;
    private final LoggingEventPropertyRepository    loggingEventPropertyRepository;
    private final ApplicationProperties             applicationProperties;

    private Iterable<LogTypeEntry> logTypes;
    private Map<String,LogTypeStatus> status;
    private List<Long> dates;

    @Autowired
    public LogTypeManager(LogTypeEntryRepository logTypeEntryRepository,
                          LoggingEventRepository loggingEventRepository,
                          LoggingEventExceptionRepository loggingEventExceptionRepository,
                          LoggingEventPropertyRepository loggingEventPropertyRepository,
                          ApplicationProperties applicationProperties) {
        this.logTypes = null;
        this.logTypeEntryRepository = logTypeEntryRepository;
        this.loggingEventRepository = loggingEventRepository;
        this.loggingEventExceptionRepository = loggingEventExceptionRepository;
        this.loggingEventPropertyRepository = loggingEventPropertyRepository;
        this.applicationProperties = applicationProperties;
    }

    private long getDateAsLog(Calendar date) {
        long result = 0;

        result += date.get(Calendar.YEAR) * 10000;
        result += ( date.get(Calendar.MONTH) + 1 ) * 100;
        result += date.get(Calendar.DATE);

        return result;
    }

    @PostConstruct
    public void initialise() {
        LOG.info("Initialise Log Type manager");

        // Get the types.
        this.logTypes = logTypeEntryRepository.findAll();

        // Setup the data.
        rolloverDate();
    }

    private String getResourceIntoString(String resourceName) {

        StringBuilder result = new StringBuilder("");

        try {
            InputStream in = getClass().getResourceAsStream(resourceName);

            if(in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                LOG.warn("Unable to load resource {}", resourceName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    public String getClassForType(String type) {
        for(LogTypeEntry nextEntry : this.logTypes) {
            if(nextEntry.getId().equalsIgnoreCase(type)) {
                return nextEntry.getLogClass();
            }
        }

        LOG.warn("Unable to determine class {}", type);
        return "UnknownClass";
    }

    public String getImage ( String type, String id ) {
        // If type image required, get from database data.
        if(type.equalsIgnoreCase("type")) {
            if(status.containsKey(id)) {
                LOG.info("Return type image.");
                return status.get(id).logTypeEntry().getImage();
            }
        }

        // If the date image required, generate.
        if(type.equalsIgnoreCase("date")) {
            Long idValue = Long.parseLong(id);

            // If the id is the same as the first date then use the today icon.
            if(dates.get(0).equals(idValue)) {
                LOG.info("Return today image.");
                return getResourceIntoString("/image/today.svg");
            } else {
                // Which date is this?
                for(int i = 1; i < dates.size(); i++ ) {
                    if(dates.get(i).equals(idValue)) {
                        LOG.info("Return previous day image.");
                        return getResourceIntoString("/image/previousday.svg").replaceAll("##ID##","-" + Integer.toString(i));
                    }
                }
            }
        }


        LOG.info("Return default image.");
        return  getResourceIntoString("/image/questionmark.svg");
    }

    public Iterable<ExternalLogTypeEntry> getLogTypes() {
        List<ExternalLogTypeEntry> activeTypes = new ArrayList<>();

        for(LogTypeStatus nextType : this.status.values()) {
            if(nextType.isActive()) {
                activeTypes.add(new ExternalLogTypeEntry(nextType.logTypeEntry()));
            }
        }

        return activeTypes;
    }

    public Iterable<LogDateEntry> getLogDates() {
        List<LogDateEntry> dates = new ArrayList<>();

        int count = 0;
        for(Long nextDate : this.dates) {
            String name = ( count == 0 ) ? "Today" : "Today - " + Integer.toString(count);

            dates.add(new LogDateEntry(nextDate,name));

            count++;
        }

        return dates;
    }

    public boolean isValidType(String type) {
        return this.status.containsKey(type);
    }

    public void rolloverDate() {
        // Setup the date values that are valid.
        dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        long maxTimeStamp;

        if(applicationProperties.getCalendar() != null) {
            try {
                // Set the calendar date to the specified date (used for testing)
                LOG.info("DATE IS NOT TODAY " + applicationProperties.getCalendar());

                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

                Date date = formatter.parse(applicationProperties.getCalendar());

                cal.setTime(date);
            } catch (Exception ex) {

            }
        }

        for(int i = 0; i < MAX_LOG_DAYS; i++) {
            dates.add(getDateAsLog(cal));
            cal.add(Calendar.DATE,-1);
        }

        cal.add(Calendar.DATE,-1);
        cal.set(Calendar.HOUR,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,900);
        maxTimeStamp = cal.getTimeInMillis();

        maxTimeStamp /= 1000;
        maxTimeStamp *= 1000;

        // Revove all entries in Logging Event Exception & Logging Event Property
        LOG.info("clear out Logging Event Property");
        loggingEventPropertyRepository.deleteAll();

        LOG.info("clear out Logging Event Exception");
        loggingEventExceptionRepository.deleteAll();

        // Remove entries that are older than days kept.
        LOG.info("Remove log entries less than {}", maxTimeStamp);
        loggingEventRepository.deleteByTimeStampBefore(maxTimeStamp);

        // Create status list.
        status = new HashMap<>();

        // Setup data on each type.
        for(LogTypeEntry nextEntry : this.logTypes) {
            LOG.info("Process Log Type {}", nextEntry.getId());

            LogTypeStatus newStatus = new LogTypeStatus(nextEntry);

            // Are there any log entries for this type?
            //noinspection unchecked
            List<LoggingEvent> logEntries = loggingEventRepository.findAll(Specification.where(logIsLikeClass(newStatus.getTypeClass())));
            if(logEntries.size() > 0) {
                newStatus.setActive();
                LOG.info("Process Log Type {} is active", nextEntry.getId());
            }

            status.put(newStatus.getId(), newStatus);
        }
    }
}
