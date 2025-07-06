package org.singhav.sample.config.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "custom.http-client")
public class HttpClientProperties {
    private Map<String, TimeoutSettings> timeouts;

    @Getter
    @Setter
    public static class TimeoutSettings {
        private int connectTimeout;
        private int readTimeout;
    }
}

