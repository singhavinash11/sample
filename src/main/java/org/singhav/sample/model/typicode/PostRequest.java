package org.singhav.sample.model.typicode;

import lombok.Data;

@Data
public class PostRequest {
    private Integer userId;
    private String title;
    private String body;
}
