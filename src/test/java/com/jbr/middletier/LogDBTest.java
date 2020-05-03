package com.jbr.middletier;

import com.jbr.middletier.log.config.ApplicationProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
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
//    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        HttpMessageConverter mappingJackson2HttpMessageConverter;
        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        LOG.info("Setup.");

        // Setup the mock web context.
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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

    private Calendar getLogDate(int days) throws ParseException {
        Calendar cal = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        Date date = formatter.parse(applicationProperties.getCalendar());

        cal.setTime(date);

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
                .andExpect(jsonPath("$[0].eventId", is (3)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].formattedMessage", is ("Podcast Manager started up.")))
                .andExpect(jsonPath("$[0].levelString", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].eventId", is (1)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].formattedMessage", is ("Podcast Manager started up 2.")))
                .andExpect(jsonPath("$[1].levelString", is ("INFO")))
                .andExpect(jsonPath("$[2].date", is (logDate)))
                .andExpect(jsonPath("$[2].eventId", is (2)))
                .andExpect(jsonPath("$[2].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[2].formattedMessage", is ("Podcast Manager initialising.")))
                .andExpect(jsonPath("$[2].levelString", is ("WARN")))
                .andExpect(jsonPath("$[3].date", is (logDate)))
                .andExpect(jsonPath("$[3].eventId", is (4)))
                .andExpect(jsonPath("$[3].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[3].formattedMessage", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[3].levelString", is ("WARN")));
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
                .andExpect(jsonPath("$[0].eventId", is (5)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].formattedMessage", is ("Podcast Manager checking. 1")))
                .andExpect(jsonPath("$[0].levelString", is ("INFO")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].eventId", is (6)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].formattedMessage", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].levelString", is ("WARN")));
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
                .andExpect(jsonPath("$[0].eventId", is (7)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].formattedMessage", is ("Podcast Manager started up. 2")))
                .andExpect(jsonPath("$[0].levelString", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].eventId", is (8)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].formattedMessage", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].levelString", is ("WARN")));
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
                .andExpect(jsonPath("$[0].eventId", is (9)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].formattedMessage", is ("Podcast Manager started up. 3")))
                .andExpect(jsonPath("$[0].levelString", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].eventId", is (10)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].formattedMessage", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].levelString", is ("WARN")));
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
                .andExpect(jsonPath("$[0].eventId", is (11)))
                .andExpect(jsonPath("$[0].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[0].formattedMessage", is ("Podcast Manager started up. 4")))
                .andExpect(jsonPath("$[0].levelString", is ("DEBUG")))
                .andExpect(jsonPath("$[1].date", is (logDate)))
                .andExpect(jsonPath("$[1].eventId", is (12)))
                .andExpect(jsonPath("$[1].typeId", is ("com.jbr.middletier.podcast.manage.PodcastManager")))
                .andExpect(jsonPath("$[1].formattedMessage", is ("Podcast Manager stopped.")))
                .andExpect(jsonPath("$[1].levelString", is ("WARN")));

    }

    @Test
    public void CheckDay6Log() throws Exception {
        // Setup the date.
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = getLogDate(-5);

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

        mockMvc.perform(get("/jbr/ext/log/data?type=TST2&date=" + dateFormat.format(cal.getTime())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(getContentType()))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
