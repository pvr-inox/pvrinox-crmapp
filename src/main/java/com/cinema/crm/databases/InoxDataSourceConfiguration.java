package com.cinema.crm.databases;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    basePackages = "com.cinema.crm.databases.pvrinox.repositories",
    entityManagerFactoryRef = "inoxEntityManagerFactory",
    transactionManagerRef = "inoxTransactionManager"
)
public class InoxDataSourceConfiguration {

    @Primary
    @Bean(name = "inoxDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.inox")
    public DataSource inoxDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "inoxEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean inoxEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("inoxDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.cinema.crm.databases.pvrinox.entities")
                .persistenceUnit("inox")
                .build();
    }

    @Primary
    @Bean(name = "inoxTransactionManager")
    public PlatformTransactionManager inoxTransactionManager(
            @Qualifier("inoxEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
