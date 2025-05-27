package com.lavesh.google.cloud.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource({"classpath:gcloud.properties"})
@ConfigurationProperties(prefix = "app.gcloud.client")
public class GcloudConfiguration {

    private String apiKey;
}
