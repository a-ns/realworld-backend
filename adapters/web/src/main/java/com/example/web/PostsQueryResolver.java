package com.example.web;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.Post;
import com.example.ports.out.GetPostsPort;
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
