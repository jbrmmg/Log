package com.jbr.middletier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * Created by jason on 31/05/16.
 */

@Configuration
@ComponentScan("com.jbr.middletier")
public class LogDBConfig {
    @Value("${middle.tier.log.db.url}")
    private String url;

    @Value("${middle.tier.log.db.username}")
    private String username;

    @Value("${middle.tier.log.db.password}")
    private String password;

    @Value("${middle.tier.log.db.driver}")
    private String driver;

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        return dataSourceBuilder.build();
    }
}
