package com.example.application.domain.ports.out;

import com.example.application.domain.model.Comment;

public interface SaveCommentPort {
    Comment save(Comment input);
}
