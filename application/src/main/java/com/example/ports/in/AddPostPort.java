package com.example.ports.in;

import com.example.Post;
import java.util.List;

public interface AddPostPort {
  Post addPost(String title, String text, String author, String category);

  List<Post> getPosts();
}
