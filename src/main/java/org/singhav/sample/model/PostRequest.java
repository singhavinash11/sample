package org.singhav.sample.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PostRequest {
    private Integer userId;
    private String title;
    private String body;
}
