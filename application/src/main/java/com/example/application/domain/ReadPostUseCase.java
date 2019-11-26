package com.example.application.domain;

import com.example.application.domain.model.Post;

import java.util.List;

public interface ReadPostUseCase {
    List<Post> getPosts();
}
