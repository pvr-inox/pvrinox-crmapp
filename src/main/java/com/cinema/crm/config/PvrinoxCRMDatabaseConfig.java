package com.cinema.crm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.cinema.crm.databases.pvrinoxcrm.repositories",
    entityManagerFactoryRef = "pvrinoxcrmEntityManagerFactory",
    transactionManagerRef = "pvrinoxcrmTransactionManager"
)
public class PvrinoxCRMDatabaseConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.crm")
    public DataSource pvrinoxcrmDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean pvrinoxcrmEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(pvrinoxcrmDataSource())
            .packages("com.cinema.crm.databases.pvrinoxcrm.entities")
            .persistenceUnit("pvrinoxcrm")
            .build();
    }

    @Bean
    public PlatformTransactionManager pvrinoxcrmTransactionManager(
            @Qualifier("pvrinoxcrmEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
