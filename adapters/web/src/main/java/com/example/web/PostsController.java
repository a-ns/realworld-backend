package com.example.web;

import com.example.Post;
import com.example.ports.in.GetPostsPort;
import com.example.ports.out.AddPostPort;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

  private final GetPostsPort getPostsPort;

  private final AddPostPort addPostPort;

  @GetMapping
  public List<Post> getAllPosts() {
    return getPostsPort.getPosts();
  }

  @PostMapping
  public Post createPost(@RequestBody Post body) {
    return this.addPostPort.addPost(
        body.getTitle(), body.getText(), body.getAuthor(), body.getCategory());
  }
}
