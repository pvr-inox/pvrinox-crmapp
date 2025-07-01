package com.cinema.crm.databases;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties
@EnableJpaRepositories(
    basePackages = "com.cinema.crm.databases.pvrinoxcrm.repositories",
    entityManagerFactoryRef = "pvrEntityManagerFactory",
    transactionManagerRef = "pvrTransactionManager"
)
public class PvrDataSourceConfiguration {

    @Bean(name = "pvrDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pvr")
    public DataSource pvrDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pvrEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pvrEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("pvrDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.cinema.crm.databases.pvrinoxcrm.entities")
                .persistenceUnit("pvr")
                .build();
    }

    @Bean(name = "pvrTransactionManager")
    public PlatformTransactionManager pvrTransactionManager(
            @Qualifier("pvrEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
