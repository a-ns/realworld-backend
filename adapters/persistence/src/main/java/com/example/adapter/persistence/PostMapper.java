package com.example.adapter.persistence;

import com.example.Post;
import org.springframework.stereotype.Component;

@Component
class PostMapper {

  Post mapToDomainEntity(PostJpaEntity postJpaEntity) {
    return Post.builder()
        .id(postJpaEntity.getId())
        .author(postJpaEntity.getAuthor())
        .text(postJpaEntity.getText())
        .title(postJpaEntity.getTitle())
        .category(postJpaEntity.getCategory())
        .build();
  }

  PostJpaEntity mapToJpaEntity(Post post) {
    return PostJpaEntity.builder()
        .id(post.getId())
        .author(post.getAuthor())
        .text(post.getText())
        .title(post.getTitle())
        .category(post.getCategory())
        .build();
  }
}
