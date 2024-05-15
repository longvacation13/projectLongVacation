package com.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Deprecated
@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    Environment env;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("driverClassName"));
        dataSourceBuilder.url(env.getProperty("url"));
        dataSourceBuilder.username(env.getProperty("username"));
        dataSourceBuilder.password(env.getProperty("password"));

        return dataSourceBuilder.build();
    }
}
