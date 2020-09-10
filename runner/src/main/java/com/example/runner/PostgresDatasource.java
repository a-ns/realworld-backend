package com.example.runner;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class PostgresDatasource {
  
  // @Bean
  public HikariDataSource hikariDataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }
}
