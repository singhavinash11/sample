package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.singhav.sample.constant.RestConstants.DEFAULT;

@Service
@RequiredArgsConstructor
public class RestClientProviderService {
    private final Map<String, RestClient> restClients;

    public RestClient getRestClient(String clientName) {
        return restClients.getOrDefault(clientName, restClients.get(DEFAULT));
    }
}
