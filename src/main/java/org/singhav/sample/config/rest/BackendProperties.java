package org.singhav.sample.config.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class BackendProperties {
    private Map<String, ApiDetail> backends;

    @Getter
    @Setter
    public static class ApiDetail {
        private String host;
        private String uri;
    }
}
