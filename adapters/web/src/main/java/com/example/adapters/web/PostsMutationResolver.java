package com.example.adapters.web;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.application.domain.CreatePostUseCase;
import com.example.application.domain.UpdatePostUseCase;
import com.example.application.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PostsMutationResolver implements GraphQLMutationResolver {

  private CreatePostUseCase createPost;
  private UpdatePostUseCase updatePost;

  public Post addPost(PostInput input) {
    try {
      return this.createPost.createPost(
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
    return this.updatePost.updatePost(
        id,
        Post.builder()
            .author(input.getAuthor())
            .category(input.getCategory())
            .text(input.getText())
            .title(input.getTitle())
            .build());
  }
}
