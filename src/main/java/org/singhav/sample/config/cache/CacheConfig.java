package org.singhav.sample.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            return paramMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey()) //To ensure consistent order
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
        };
    }

    //@SuppressWarnings("unchecked")
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
}
