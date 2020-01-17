package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.exceptions.CommentNotFoundException;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.model.User;
import lombok.Builder;
import lombok.Data;


public interface DeleteCommentOnArticleUseCase {

    Comment delete(DeleteCommentCommand input) throws CommentNotFoundException, ArticleNotFoundException;

    @Data
    @Builder
    class DeleteCommentCommand {
        CommentId commentId;
        String slug;
        User requester;
    }
}
