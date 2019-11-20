package com.example.adapters.web;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.application.domain.ports.out.GetPostsPort;
import com.example.application.domain.model.Post;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostsQueryResolver implements GraphQLQueryResolver {

  private GetPostsPort getPostPort;

  public List<Post> getRecentPosts(Integer count, Integer offset) {

    return getPostPort.getPosts();
  }
}
