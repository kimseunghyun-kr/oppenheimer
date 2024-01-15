//package com.stock.oppenheimer.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class JPADataSourceConfiguration {
//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties JPADataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource JPADataSource() {
//        return JPADataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }
//}
