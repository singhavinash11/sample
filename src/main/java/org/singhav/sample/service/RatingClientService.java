package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.aspect.LogExecutionTime;
import org.singhav.sample.model.rating.RatingRequest;
import org.singhav.sample.model.rating.RatingResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static org.singhav.sample.constant.RestConstants.CRISIL_BASE_URL;
import static org.singhav.sample.constant.RestConstants.CRISIL_RATING_URI;
import static org.singhav.sample.constant.RestConstants.DEFAULT;
import static org.singhav.sample.constant.RestConstants.HTTPS;
import static org.singhav.sample.util.CommonUtils.createParamMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingClientService {
    private final RestClientProviderService restClientProviderService;

    @LogExecutionTime
    @Cacheable(cacheNames = "ratingCache", keyGenerator = "ratingKeyGenerator")
    public RatingResponse fetchMutualFundRating(Map<String, String> params) {
        return getRatingResponse(params);
    }

    @LogExecutionTime
    public RatingResponse fetchMutualFundRating(RatingRequest ratingRequest) {
        Map<String, String> paramMap = createParamMap(ratingRequest);
        return getRatingResponse(paramMap);
    }

    private RatingResponse getRatingResponse(Map<String, String> paramMap) {
        ResponseEntity<RatingResponse> ratingResponseEntity = restClientProviderService.getRestClient(DEFAULT)
                .get()
                .uri(uriBuilder -> buildUri(uriBuilder, paramMap))
                .retrieve()
                .toEntity(RatingResponse.class);
        return ratingResponseEntity.getBody();
    }

    private static URI buildUri(UriBuilder uriBuilder, Map<String, String> queryParams) {
        UriBuilder builder = uriBuilder.scheme(HTTPS)
                .host(CRISIL_BASE_URL)
                .path(CRISIL_RATING_URI);
        queryParams.forEach((key, value) -> builder.queryParamIfPresent(key, Optional.ofNullable(value)));
        return builder.build();
    }
}
