package com.example.application.domain;

import com.example.application.domain.model.Post;

public interface UpdatePostUseCase {
    Post updatePost(Integer id, Post post);
}
