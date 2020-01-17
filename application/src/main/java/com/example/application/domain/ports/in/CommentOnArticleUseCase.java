package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

public interface CommentOnArticleUseCase {

  Comment publishComment(PublishCommentCommand input)
      throws UserNotFoundException, ArticleNotFoundException;

  @Data
  @Builder
  class PublishCommentCommand {

    @NonNull private String body;
    @NonNull private String articleSlug;
    @NonNull private User commentAuthor;
  }
}
