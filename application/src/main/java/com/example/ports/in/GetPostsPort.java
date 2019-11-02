package com.example.ports.in;

import com.example.Post;
import java.util.List;

public interface GetPostsPort {
  List<Post> getPosts();

  Post getPost(Integer id);
}
