package org.singhav.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.singhav.sample.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private HelloService helloService;

    @Test
    void getTodosWithoutIdShouldReturnOkAndServiceResponse() throws Exception {
        //Given
        String mockResponse = "[{\"id\": 1, \"title\": \"Todo 1\"}]";

        //When
        when(helloService.getTodos(null)).thenReturn(ResponseEntity.ok(mockResponse));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(mockResponse));
    }

    @Test
    void getTodosWithIdShouldReturnOkAndServiceResponse() throws Exception {
        //Given
        String mockResponse = "{\"id\": 2, \"title\": \"Todo 2\"}";

        //When
        when(helloService.getTodos(2)).thenReturn(ResponseEntity.ok(mockResponse));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(mockResponse));
    }

    @Test
    void getPostsWithoutIdShouldReturnOkAndServiceResponse() throws Exception {
        //Given
        String mockResponse = "[{\"id\": 101, \"title\": \"Post 1\"}]";

        //When
        when(helloService.getPosts(null)).thenReturn(ResponseEntity.ok(mockResponse));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(mockResponse));
    }

    @Test
    void getPostsWithIdShouldReturnOkAndServiceResponse() throws Exception {
        //Given
        String mockResponse = "{\"id\": 102, \"title\": \"Post 2\"}";

        //When
        when(helloService.getPosts(102)).thenReturn(ResponseEntity.ok(mockResponse));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/102"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(mockResponse));
    }

    @Test
    void getCommentsLinkedWithPostShouldReturnOkAndServiceResponse() throws Exception {
        //Given
        String mockResponse = "[{\"id\": 1, \"postId\": 201, \"body\": \"Comment 1\"}]";

        //When
        when(helloService.getCommentsLinkedWithPost(201)).thenReturn(ResponseEntity.ok(mockResponse));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/201/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(mockResponse));
    }

    /*@Test
    void savePostShouldReturnCreatedAndServiceResponse() throws Exception {
        PostRequest request = new PostRequest();
        request.setTitle("New Post");
        PostRequest mockResponse = new PostRequest();
        mockResponse.setId(301);
        mockResponse.setTitle("New Post");

        when(helloService.savePost(any(PostRequest.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void updatePostShouldReturnOkAndServiceResponse() throws Exception {
        int postId = 401;
        PostRequest request = new PostRequest();
        request.setTitle("Updated Post");
        PostRequest mockResponse = new PostRequest();
        mockResponse.setId(postId);
        mockResponse.setTitle("Updated Post");

        when(helloService.updatePost(eq(postId), any(PostRequest.class))).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.put("/posts/" + postId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void patchPostShouldReturnOkAndServiceResponse() throws Exception {
        int postId = 501;
        PostRequest request = new PostRequest();
        request.setBody("Patched Body");
        PostRequest mockResponse = new PostRequest();
        mockResponse.setId(postId);
        mockResponse.setTitle("Existing Title"); // Assuming title remains the same
        mockResponse.setBody("Patched Body");

        when(helloService.patchPost(eq(postId), any(PostRequest.class))).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/" + postId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    void deletePostShouldReturnOkAndServiceResponse() throws Exception {
        int postId = 601;
        PostRequest mockResponse = new PostRequest();
        mockResponse.setId(postId);

        when(helloService.deletePost(postId)).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(mockResponse)));
    }*/
}