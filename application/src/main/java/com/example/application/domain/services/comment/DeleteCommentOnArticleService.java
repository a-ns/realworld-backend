package com.example.application.domain.services.comment;

import com.example.application.domain.exceptions.CommentNotFoundException;
import com.example.application.domain.model.Comment;
import com.example.application.domain.ports.in.DeleteCommentOnArticleUseCase;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.out.DeleteCommentPort;
import com.example.application.domain.ports.out.LoadCommentPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class DeleteCommentOnArticleService implements DeleteCommentOnArticleUseCase {

  private LoadCommentPort loadCommentPort;
  private GetArticleQuery getArticleQuery;
  private DeleteCommentPort deleteCommentPort;

  @Override
  public Comment delete(DeleteCommentCommand input) {

    Comment existingComment =
        this.loadCommentPort
            .getComment(input.getCommentId())
            .orElseThrow(CommentNotFoundException::new);
    this.getArticleQuery.getArticle(input.getSlug(), input.getRequester());

    this.deleteCommentPort.delete(input.getCommentId());
    return existingComment;
  }
}
