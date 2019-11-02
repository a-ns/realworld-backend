package com.example.web;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.Post;
import com.example.PostService;
import com.example.ports.out.AddPostPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class PostsMutationResolver implements GraphQLMutationResolver {

  @Autowired private AddPostPort postDao;

  @Autowired private PostService service;

  public Post addPost(PostInput input) {
    try {
      return this.postDao.addPost(
          input.getTitle(), input.getText(), input.getAuthor(), input.getCategory());
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
