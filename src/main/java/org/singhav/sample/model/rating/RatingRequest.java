package org.singhav.sample.model.rating;

public record RatingRequest(
        String categoryName,
        String displayName,
        String fundName,
        String invType,
        String start,
        String limit,
        String planName
) {
}
