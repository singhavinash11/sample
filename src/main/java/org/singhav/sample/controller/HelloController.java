package org.singhav.sample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.model.PostRequest;
import org.singhav.sample.model.PostResponse;
import org.singhav.sample.service.HelloService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.singhav.sample.constant.RestConstants.POSTS_ID_URI;
import static org.singhav.sample.constant.RestConstants.POSTS_URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {
    private final ObjectMapper objectMapper;
    private final HelloService helloService;

    @GetMapping(value = {"/todos", "/todos/{id}"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTodos(@PathVariable(required = false) Integer id) {
        log.info("Calling 'GET /todos' with id :: {} using {}", id, Thread.currentThread());
        return helloService.getTodos(id);
    }

    @GetMapping(value = {POSTS_URI, POSTS_ID_URI}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPosts(@PathVariable(required = false) Integer id) {
        return helloService.getPosts(id);
    }

    @GetMapping(value = "/posts/{id}/comments", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCommentsLinkedWithPost(@PathVariable(required = false) Integer id) {
        return helloService.getCommentsLinkedWithPost(id);
    }

    @PostMapping(value = POSTS_URI, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> savePost(@Valid @RequestBody PostRequest postRequest) throws JsonProcessingException {
        PostResponse savedPost = helloService.savePost(postRequest);
        log.info("Response :: {}", objectMapper.writeValueAsString(savedPost));
        return new ResponseEntity<>(savedPost, HttpStatusCode.valueOf(201));
    }

    @PutMapping(POSTS_ID_URI)
    public ResponseEntity<PostResponse> updatePost(@PathVariable Integer id, @RequestBody @Valid PostRequest postRequest) {
        return helloService.updatePost(id, postRequest);
    }

    @PatchMapping(POSTS_ID_URI)
    public ResponseEntity<PostResponse> patchPost(@PathVariable Integer id, @RequestBody @Valid PostRequest postRequest) {
        return helloService.patchPost(id, postRequest);
    }

    @DeleteMapping(POSTS_ID_URI)
    public ResponseEntity<PostResponse> deletePost(@PathVariable Integer id) {
        return helloService.deletePost(id);
    }
}
