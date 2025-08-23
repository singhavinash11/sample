package org.singhav.sample.config.rest;

import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.config.rest.HttpClientProperties.TimeoutSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RestClientConfig {

    @Bean
    public Map<String, RestClient> restClients(HttpClientProperties httpClientProperties) {
        final Map<String, RestClient> clientMap = HashMap.newHashMap(httpClientProperties.getTimeouts().size());
        httpClientProperties.getTimeouts()
                .forEach((key, settings) -> clientMap.put(key, createClient(settings)));
        return clientMap;
    }

    private static RestClient createClient(TimeoutSettings timeoutSettings) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(timeoutSettings.getConnectTimeout());
        requestFactory.setReadTimeout(timeoutSettings.getReadTimeout());

        ClientHttpRequestInterceptor timingInterceptor = (request, body, execution) -> {
            long start = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long duration = System.currentTimeMillis() - start;
            log.debug("REST call to {} {} ({}) took {} ms", request.getMethod(), request.getURI(), response.getStatusCode(), duration);
            return response;
        };
        return RestClient.builder()
                .requestFactory(requestFactory)
                .requestInterceptor(timingInterceptor)
                .build();
    }
}
