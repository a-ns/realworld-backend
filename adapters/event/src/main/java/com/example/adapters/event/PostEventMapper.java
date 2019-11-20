package com.example.adapters.event;

import com.example.application.domain.model.Post;
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
