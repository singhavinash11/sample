package org.singhav.sample.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RestConstants {
    public static final String HTTPS = "https";

    public static final String TYPICODE_BASE_URL = "jsonplaceholder.typicode.com";
    public static final String POSTS_URI = "/posts";
    public static final String POSTS_ID_URI = "/posts/{id}";

    public static final String APPLICATION_VND_RATING_V2_JSON = "application/vnd.singhav.rating.v2+json";
    public static final String APPLICATION_VND_RATING_V2_OCTET_STREAM = "application/vnd.singhav.rating.v2+octet-stream";

    //RestClients
    public static final String DEFAULT = "default";
    public static final String TODOS = "todos";
    public static final String POSTS = "posts";
    public static final String RATINGS = "ratings";
}
