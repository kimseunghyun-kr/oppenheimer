//package com.stock.oppenheimer.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Objects;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.stock.oppenheimer.repository.jpaRepository",
//        entityManagerFactoryRef = "JPAEntityManagerFactory",
//        transactionManagerRef = "JPATransactionManager")
//public class JPAConfiguration {
//    @Bean
//    public LocalContainerEntityManagerFactoryBean JPAEntityManagerFactory(
//            @Qualifier("JPADataSource") DataSource dataSource,
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.stock.oppenheimer.domain")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager JPATransactionManager(
//            @Qualifier("JPAEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
//        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
//    }
//}
