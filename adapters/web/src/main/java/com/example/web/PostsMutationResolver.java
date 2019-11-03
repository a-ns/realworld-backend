package com.example.web;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.Post;
import com.example.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PostsMutationResolver implements GraphQLMutationResolver {

  private PostService service;

  public Post addPost(PostInput input) {
    try {
      return this.service.createPost(
          Post.builder()
              .title(input.getTitle())
              .text(input.getText())
              .category(input.getCategory())
              .author(input.getAuthor())
              .build());
    } catch (Exception e) {
      return null;
    }
  }

  public Post updatePost(Integer id, PostUpdateInput input) {
    return this.service.updatePost(
        id,
        Post.builder()
            .author(input.getAuthor())
            .category(input.getCategory())
            .text(input.getText())
            .title(input.getTitle())
            .build());
  }
}
