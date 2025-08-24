package org.singhav.sample.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.exception.IllegalAccessOrInvocationTargetException;
import org.singhav.sample.model.rating.RatingRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class FundRatingUtils {
    public static final String DATE_FORMAT = "MMM dd, yyyy";
    public static final DateTimeFormatter DATE_FORMATTER_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");

    public static final String FILE_NAMING_FORMAT_REGEX = " ?/ ?| ";
    public static final String UNDERSCORE = "_";

    public static HttpHeaders buildHeaders(Map<String, String> queryParamMap) {
        String invType = queryParamMap.getOrDefault("invType", EMPTY);
        String categoryName = queryParamMap.getOrDefault("categoryName", EMPTY);
        String filename = "CRISIL" + UNDERSCORE + invType + (hasText(invType) ? UNDERSCORE : EMPTY)
                + categoryName + (hasText(categoryName) ? UNDERSCORE : EMPTY)
                + LocalDateTime.now().format(DATE_FORMATTER_FILE) + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(filename.replaceAll(FILE_NAMING_FORMAT_REGEX, UNDERSCORE))
                .build());
        return headers;
    }

    public static HttpHeaders buildHeaders(RatingRequest ratingRequest) {
        Map<String, String> paramMap = createParamMap(ratingRequest);
        return buildHeaders(paramMap);
    }

    public static Map<String, String> createParamMap(RatingRequest ratingRequest) {
        return Arrays.stream(RatingRequest.class.getRecordComponents())
                .map(rc -> getParamMapEntry(ratingRequest, rc))
                .filter(Objects::nonNull)
                .filter(entry -> hasText(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> getParamMapEntry(RatingRequest ratingRequest, RecordComponent rc) {
        try {
            String value = (String) rc.getAccessor().invoke(ratingRequest);
            if (!hasText(value)) {
                log.debug("Skipping empty field: {}", rc.getName());
                return null;
            }
            return Map.entry(rc.getName(), value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalAccessOrInvocationTargetException("Exception while mapping 'RatingRequest' to Map", ex);
        }
    }
}
