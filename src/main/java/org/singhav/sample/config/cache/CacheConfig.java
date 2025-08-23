package org.singhav.sample.config.cache;

import org.singhav.sample.model.rating.RatingRequest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.singhav.sample.util.FundRatingUtils.createParamMap;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("ratingCache");
    }

    @Bean
    public KeyGenerator ratingKeyGenerator() {
        return (target, method, params) -> {
            Map<String, String> paramMap = castToMap(params[0]);
            return getSortedKey(paramMap);
        };
    }

    @Bean
    public KeyGenerator ratingRequestKeyGenerator() {
        return (target, method, params) -> {
            Map<String, String> paramMap = castToMapFromRatingRequest(params[0]);
            return getSortedKey(paramMap);
        };
    }

    private static Map<String, String> castToMap(Object obj) {
        if (obj instanceof Map<?, ?> rawMap) {
            Map<String, String> result = new HashMap<>();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                if (entry.getKey() instanceof String key && entry.getValue() instanceof String value) {
                    result.put(key, value);
                } else {
                    throw new IllegalArgumentException("Key or value is not a String: " + entry.getKey() + " -> " + entry.getValue());
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Provided object is not a Map.");
        }
    }

    private static Map<String, String> castToMapFromRatingRequest(Object obj) {
        if (obj instanceof RatingRequest ratingRequest) {
            return createParamMap(ratingRequest);
        } else {
            throw new IllegalArgumentException("Provided object is not an instance of RatingRequest");
        }
    }

    private static String getSortedKey(Map<String, String> paramMap) {
        return paramMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) //To ensure consistent order
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}
