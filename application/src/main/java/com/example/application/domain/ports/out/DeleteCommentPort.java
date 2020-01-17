package com.example.application.domain.ports.out;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;

public interface DeleteCommentPort {

    Comment delete(CommentId input);
}
