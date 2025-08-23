package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import org.singhav.sample.model.typicode.PostRequest;
import org.singhav.sample.model.typicode.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.singhav.sample.constant.RestConstants.HTTPS;
import static org.singhav.sample.constant.RestConstants.POSTS;
import static org.singhav.sample.constant.RestConstants.POSTS_ID_URI;
import static org.singhav.sample.constant.RestConstants.POSTS_URI;
import static org.singhav.sample.constant.RestConstants.TODOS;
import static org.singhav.sample.constant.RestConstants.TYPICODE_BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final RestClientProviderService restClientProviderService;

    public ResponseEntity<String> getTodos(Integer id) {
        return restClientProviderService.getRestClient(TODOS)
                .get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path("/todos")
                        .pathSegment("{id}")
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getPosts(Integer id) {
        return restClientProviderService.getRestClient(POSTS)
                .get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getCommentsLinkedWithPost(Integer id) {
        return restClientProviderService.getRestClient(POSTS)
                .get()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_ID_URI)
                        .pathSegment("comments")
                        .build(id))
                .retrieve()
                .toEntity(String.class);
    }

    public PostResponse savePost(PostRequest postRequest) {
        ResponseEntity<PostResponse> savedPostEntity = restClientProviderService.getRestClient(POSTS)
                .post()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_URI)
                        .build())
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
        return savedPostEntity.getBody();
    }

    public ResponseEntity<PostResponse> updatePost(Integer id, PostRequest postRequest) {
        return restClientProviderService.getRestClient(POSTS)
                .put()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
    }

    public ResponseEntity<PostResponse> patchPost(Integer id, PostRequest postRequest) {
        return restClientProviderService.getRestClient(POSTS)
                .patch()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .contentType(APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .toEntity(PostResponse.class);
    }

    public ResponseEntity<PostResponse> deletePost(Integer id) {
        return restClientProviderService.getRestClient(POSTS)
                .delete()
                .uri(uriBuilder -> uriBuilder.scheme(HTTPS)
                        .host(TYPICODE_BASE_URL)
                        .path(POSTS_ID_URI)
                        .build(id))
                .retrieve()
                .toEntity(PostResponse.class);
    }
}
