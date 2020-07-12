package com.example.demojpa.blog.infra.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = DataSourceConfigurationPropertiesBean.PREFIX)
@Getter @Setter
public class DataSourceConfigurationPropertiesBean {
    public static final String PREFIX = "spring.datasource";

    private String username;
    private String url;
    private String password;
    private String driverClassName;
    private JpaDialect jpaDialect;
}
