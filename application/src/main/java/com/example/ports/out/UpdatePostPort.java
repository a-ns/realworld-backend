package com.example.ports.out;

import com.example.Post;

public interface UpdatePostPort {

  Post updatePost(Integer id, Post post);
}
