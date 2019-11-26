package com.example.adapters.web;

import com.example.application.domain.CreatePostUseCase;
import com.example.application.domain.ReadPostUseCase;
import com.example.application.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

    private ReadPostUseCase postsLoader;
    private CreatePostUseCase postCreator;

  @GetMapping
  public List<Post> getAllPosts() {
      return postsLoader.getPosts();
  }

  @PostMapping
  public Post createPost(@RequestBody Post body) {
      return this.postCreator.createPost(body);
  }
}
