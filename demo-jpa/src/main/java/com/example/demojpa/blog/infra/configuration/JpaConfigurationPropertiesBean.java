package com.example.demojpa.blog.infra.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = JpaConfigurationPropertiesBean.PREFIX)
@Getter @Setter
public class JpaConfigurationPropertiesBean {
    public static final String PREFIX = "spring.jpa";

    private boolean showSql;
    private boolean formalSql;
    private Class hibernateImplicitNamingStrategy;
    private String connectionCharset;
    private String currentSessionContextClass;
    private String autodetection;
    private Class transactionManagerLookupClass;
    private String dialect;
    private String hbm2ddl;
}
