package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.aspect.LogExecutionTime;
import org.singhav.sample.config.rest.BackendProperties.ApiDetail;
import org.singhav.sample.model.rating.RatingRequest;
import org.singhav.sample.model.rating.RatingResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static org.singhav.sample.constant.RestConstants.DEFAULT;
import static org.singhav.sample.constant.RestConstants.HTTPS;
import static org.singhav.sample.util.FundRatingUtils.createParamMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingClientService {
    private final RestDetailsProviderService restDetailsProviderService;

    @LogExecutionTime
    @Cacheable(cacheNames = "ratingCache", keyGenerator = "ratingMapKeyGenerator")
    public RatingResponse fetchMutualFundRating(Map<String, String> params) {
        return getRatingResponse(params);
    }

    @LogExecutionTime
    @Cacheable(cacheNames = "ratingCache", keyGenerator = "ratingRequestKeyGenerator")
    public RatingResponse fetchMutualFundRating(RatingRequest ratingRequest) {
        Map<String, String> paramMap = createParamMap(ratingRequest);
        return getRatingResponse(paramMap);
    }

    private RatingResponse getRatingResponse(Map<String, String> paramMap) {
        ResponseEntity<RatingResponse> ratingResponseEntity = restDetailsProviderService.getRestClient(DEFAULT)
                .get()
                .uri(uriBuilder -> {
                    var apiDetail = restDetailsProviderService.getApiDetail("crisil-ranking");
                    return buildUri(uriBuilder, paramMap, apiDetail);
                })
                .retrieve()
                .toEntity(RatingResponse.class);
        return ratingResponseEntity.getBody();
    }

    private static URI buildUri(UriBuilder uriBuilder, Map<String, String> queryParams, ApiDetail apiDetail) {
        UriBuilder builder = uriBuilder.scheme(HTTPS)
                .host(apiDetail.getHost())
                .path(apiDetail.getUri());
        queryParams.forEach((key, value) -> builder.queryParamIfPresent(key, Optional.ofNullable(value)));
        return builder.build();
    }
}
