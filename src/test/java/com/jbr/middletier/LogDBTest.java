package com.jbr.middletier;

import com.jbr.middletier.log.data.ExternalLogTypeEntry;
import com.jbr.middletier.log.data.LogTypeEntry;
import com.jbr.middletier.log.data.LogTypeManager;
import com.jbr.middletier.log.data.LoggingEvent;
import com.jbr.middletier.log.dataaccess.LogTypeEntryRepository;
import com.jbr.middletier.log.dataaccess.LoggingEventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsAfter;
import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsBetween;
import static com.jbr.middletier.log.dataaccess.LoggingEventSpecifications.logIsLikeClass;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.data.jpa.domain.Specifications.where;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiddleTier.class)
public class LogDBTest {
    final static private Logger LOG = LoggerFactory.getLogger(LogDBTest.class);

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LoggingEventRepository loggingEventRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        // Setup the mock web context.
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // Log all the data that is in the Log Event table
        Iterable<LoggingEvent> result =  loggingEventRepository.findAll(where(logIsAfter(0)),new Sort(Sort.Direction.ASC, "timeStamp"));

        StringBuilder sb = new StringBuilder();

        sb.append(System.getProperty("line.separator"));
        sb.append("--------------------------------------------------------------------------");
        sb.append(System.getProperty("line.separator"));
        sb.append("date,time,timestamp,level,seq,text,type");
        sb.append(System.getProperty("line.separator"));

        for(LoggingEvent nextEvent : result) {
            sb.append(Long.toString(nextEvent.getDate()));
            sb.append(",");
            sb.append(nextEvent.getTime().toString());
            sb.append(",");
            sb.append(nextEvent.getTimeStampString());
            sb.append(",");
            sb.append(nextEvent.getLevel());
            sb.append(",");
            sb.append(Integer.toString(nextEvent.getSequence()));
            sb.append(",");
            sb.append(nextEvent.getText());
            sb.append(",");
            sb.append(nextEvent.getTypeId());
            sb.append(System.getProperty("line.separator"));
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("--------------------------------------------------------------------------");
        LOG.info(sb.toString());
    }

    private MediaType getContentType() {
        return new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
    }

    @Test
    public void checkType() throws Exception {
        // Check the log type.
        mockMvc.perform(get("/jbr/ext/log/type"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("TST")))
                .andExpect(jsonPath("$[0].name", is ("Test Log")));
    }

    private Calendar getLogDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);

        return cal;
    }

    @Test
    public void checkDay1Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(0);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].date", is (logDate)))
                .andExpect(jsonPath("$[0].sequence", is (3)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].text", is ("Podcast Manager started up.")))
                .andExpect(jsonPath("$[0].level", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].sequence", is (1)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].text", is ("Podcast Manager started up 2.")))
                .andExpect(jsonPath("$[1].level", is ("INFO")))
                .andExpect(jsonPath("$[2].date", is (logDate)))
                .andExpect(jsonPath("$[2].sequence", is (2)))
                .andExpect(jsonPath("$[2].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[2].text", is ("Podcast Manager initialising.")))
                .andExpect(jsonPath("$[2].level", is ("WARN")))
                .andExpect(jsonPath("$[3].date", is (logDate)))
                .andExpect(jsonPath("$[3].sequence", is (4)))
                .andExpect(jsonPath("$[3].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[3].text", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[3].level", is ("WARN")));
    }

    @Test
    public void CheckDay2Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-1);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is (logDate)))
                .andExpect(jsonPath("$[0].sequence", is (5)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].text", is ("Podcast Manager checking. 1")))
                .andExpect(jsonPath("$[0].level", is ("INFO")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].sequence", is (6)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].text", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].level", is ("WARN")));
    }

    @Test
    public void CheckDay3Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-2);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is (logDate)))
                .andExpect(jsonPath("$[0].sequence", is (7)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].text", is ("Podcast Manager started up. 2")))
                .andExpect(jsonPath("$[0].level", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].sequence", is (8)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].text", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].level", is ("WARN")));
    }

    @Test
    public void CheckDay4Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-3);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is (logDate)))
                .andExpect(jsonPath("$[0].sequence", is (9)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].text", is ("Podcast Manager started up. 3")))
                .andExpect(jsonPath("$[0].level", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].sequence", is (10)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].text", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].level", is ("WARN")));
    }

    @Test
    public void CheckDay5Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-4);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date", is (logDate)))
                .andExpect(jsonPath("$[0].sequence", is (11)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].text", is ("Podcast Manager started up. 4")))
                .andExpect(jsonPath("$[0].level", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].sequence", is (12)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].text", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].level", is ("WARN")));

    }

    @Test
    public void CheckDay6Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-5);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void CheckDay1InactiveLog() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(0);

        int logDate = Integer.parseInt(dateFormat.format(cal.getTime()));
        mockMvc.perform(get("/jbr/ext/log/data?type=TST2&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
