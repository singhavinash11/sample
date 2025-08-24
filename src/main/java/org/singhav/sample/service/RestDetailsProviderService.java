package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import org.singhav.sample.config.rest.BackendProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.singhav.sample.constant.RestConstants.DEFAULT;

@Service
@RequiredArgsConstructor
public class RestDetailsProviderService {
    private final Map<String, RestClient> restClients;
    private final BackendProperties backendProperties;

    public RestClient getRestClient(String clientName) {
        return restClients.getOrDefault(clientName, restClients.get(DEFAULT));
    }

    public BackendProperties.ApiDetail getApiDetail(String apiName) {
        return backendProperties.getBackends().get(apiName);
    }
}
