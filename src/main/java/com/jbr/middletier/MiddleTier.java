package com.jbr.middletier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by jason on 15/12/15.
 *
 * Command:
 *
 * (from ~/GitDev/JobServer)
 *
 * Parameters
 *
 * -Xms768M
 * -Xmx768M
 * -XX:PermSize=256m
 * -XX:MaxPermSize=256m
 * -Dspring.config.location=file:./MiddleTier/src/config/MiddleTier-LogDB-DBG.properties
 * -Dlog4j.configuration=file:./MiddleTier/src/config/MiddleTier-LogDB-DBG-log4j.xml
 *
 *
 * Useful links:
 *
 * Spring Boot - RESTFul  Web Service with Boot Actuator
 * Spring      - Access relation data with jdbc
 * Spring      - Access data with JPA
 * http://stackoverflow.com/questions/27614301/spring-boot-multiple-datasource
 */

@SpringBootApplication
@EnableScheduling
public class MiddleTier {
    public static void main(String[] args) {
        SpringApplication.run(LogDBConfig.class, args);
    }
}
