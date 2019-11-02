package com.example.ports.in;

import com.example.Post;
import java.util.List;
import java.util.Optional;

public interface GetPostsPort {
  List<Post> getPosts();

  Optional<Post> getPost(Integer id);
}
