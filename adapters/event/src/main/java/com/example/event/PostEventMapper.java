package com.example.event;

import com.example.Post;
import org.springframework.stereotype.Component;

@Component
class PostEventMapper {

  Post eventDataToDomain(PostEvent event) {
    return Post.builder()
        .author(event.getAuthor())
        .category(event.getCategory())
        .text(event.getText())
        .title(event.getTitle())
        .build();
  }
}
