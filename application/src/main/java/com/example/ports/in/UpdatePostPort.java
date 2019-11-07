package com.example.ports.in;

import com.example.Post;

public interface UpdatePostPort {

  Post updatePost(Integer id, Post post);
}
