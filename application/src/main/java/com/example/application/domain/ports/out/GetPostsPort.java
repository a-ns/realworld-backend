package com.example.application.domain.ports.out;

import com.example.application.domain.model.Post;
import java.util.List;

public interface GetPostsPort {
  List<Post> getPosts();

  Post getPost(Integer id);
}
