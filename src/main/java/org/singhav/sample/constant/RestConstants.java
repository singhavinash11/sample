package org.singhav.sample.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RestConstants {
    public static final String HTTPS = "https";

    public static final String TYPICODE_BASE_URL = "jsonplaceholder.typicode.com";
    public static final String POSTS_URI = "/posts";
    public static final String POSTS_ID_URI = "/posts/{id}";

    public static final String CRISIL_BASE_URL = "intelligence.crisil.com";
    public static final String CRISIL_RATING_URI = "/content/intelligence/en/homepage/what-we-do/research/investment-research-product/mutual-fund-research/mutual-fund-ranking/_jcr_content/content_par/columncontrol_128464/col-100-1-wp/box/col-100-1/tabs_copy/1/mf_rating.mfRating.json";

    public static final String APPLICATION_VND_RATING_V2_JSON = "application/vnd.singhav.rating.v2+json";
    public static final String APPLICATION_VND_RATING_V2_OCTET_STREAM = "application/vnd.singhav.rating.v2+octet-stream";

    //RestClients
    public static final String DEFAULT = "default";
    public static final String TODOS = "todos";
    public static final String POSTS = "posts";
}
