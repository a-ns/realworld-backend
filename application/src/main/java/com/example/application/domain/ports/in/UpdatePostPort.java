package com.example.application.domain.ports.in;

import com.example.application.domain.model.Post;

public interface UpdatePostPort {

  Post updatePost(Integer id, Post post);
}
