package com.example.demo.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cop-security")
@Getter @Setter
public class SecurityConfiguration {
    private String mobileAppClientId;
    private String mobileAppClientSecret;

}
