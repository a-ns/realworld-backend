package com.example.web;

import com.example.Post;
import com.example.ports.in.GetPostsPort;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

  private final GetPostsPort getPostsPort;

  @GetMapping
  public List<Post> getAllPosts() {
    return getPostsPort.getPosts();
  }
}
