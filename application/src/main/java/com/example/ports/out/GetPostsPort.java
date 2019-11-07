package com.example.ports.out;

import com.example.Post;
import java.util.List;

public interface GetPostsPort {
  List<Post> getPosts();

  Post getPost(Integer id);
}
