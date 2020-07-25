package com.example.retroapp.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
    @Autowired
    private DataSourceConfigurationPropertiesBean dataSourceProperties;

    @Autowired
    private JpaConfigurationPropertiesBean jpaConfigurationProperties;

//    @Bean
//    public LocalEntityManagerFactoryBean entityManagerFactoryBean() {
//
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setDatabasePlatform(jpaConfigurationProperties.getDialect());
//
//        return jpaVendorAdapter;
//    }
//
//    @Bean
//    public JpaDialect jpaDialect() {
//        return new HibernateJpaDialect();
//    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder factory = DataSourceBuilder.create(this.getClass().getClassLoader())
                .driverClassName(this.dataSourceProperties.getDriverClassName())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword());
        return factory.build();
    }
}
