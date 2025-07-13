package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.model.PostRequest;
import org.singhav.sample.model.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.singhav.sample.constant.RestConstants.BASE_URL;
import static org.singhav.sample.constant.RestConstants.DEFAULT;
import static org.singhav.sample.constant.RestConstants.HTTPS;
import static org.singhav.sample.constant.RestConstants.POSTS;
import static org.singhav.sample.constant.RestConstants.POSTS_ID_URI;
import static org.singhav.sample.constant.RestConstants.POSTS_URI;
import static org.singhav.sample.constant.RestConstants.TODOS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloService {
    private static final String LOGGER_PREFIX_REST_CLIENT = "RestClient -> {}";

    private final Map<String, RestClient> restClients;

    public ResponseEntity<String> getTodos(Integer id) {
        RestClient restClient = getRestClient(TODOS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path("/todos")
                        .pathSegment("{id}")
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getPosts(Integer id) {
        if (id != null) {
            log.info("Calling 'GET /posts' with id :: {} using {}", id, Thread.currentThread());
        } else {
            log.info("Calling 'GET /posts' using {}", Thread.currentThread());
        }
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getCommentsLinkedWithPost(Integer id) {
        log.info("Calling 'GET comments associated with posts' with id :: {} using {}", id, Thread.currentThread());
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_ID_URI)
                        .pathSegment("comments")
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public PostResponse savePost(PostRequest postRequest) {
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        ResponseEntity<PostResponse> savedPostEntity = restClient.post()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_URI)
                        .build())
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
        return savedPostEntity.getBody();
    }

    public ResponseEntity<PostResponse> updatePost(Integer id, PostRequest postRequest) {
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.put()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
    }

    public ResponseEntity<PostResponse> patchPost(Integer id, PostRequest postRequest) {
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.patch()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
    }

    public ResponseEntity<PostResponse> deletePost(Integer id) {
        RestClient restClient = getRestClient(POSTS);
        log.info(LOGGER_PREFIX_REST_CLIENT, restClient);
        return restClient.delete()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .retrieve()
                .toEntity(PostResponse.class);
    }

    private RestClient getRestClient(String clientName) {
        return restClients.getOrDefault(clientName, restClients.get(DEFAULT));
    }
}
