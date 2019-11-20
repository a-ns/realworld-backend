package com.example.application.domain.ports.in;

import com.example.application.domain.model.Post;
import java.util.List;

public interface AddPostPort {
  Post addPost(String title, String text, String author, String category);

  List<Post> getPosts();
}
