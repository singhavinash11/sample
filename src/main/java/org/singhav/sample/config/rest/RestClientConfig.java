package org.singhav.sample.config.rest;

import org.singhav.sample.config.rest.HttpClientProperties.TimeoutSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RestClientConfig {

    @Bean
    public Map<String, RestClient> restClients(HttpClientProperties httpClientProperties) {
        Map<String, RestClient> clientMap = new HashMap<>();
        httpClientProperties.getTimeouts()
                .forEach((key, settings) -> clientMap.put(key, createClient(settings)));
        return clientMap;
    }

    private static RestClient createClient(TimeoutSettings timeoutSettings) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeoutSettings.getConnectTimeout());
        requestFactory.setReadTimeout(timeoutSettings.getReadTimeout());
        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }
}
