package com.example.application.domain.services.comment;

import com.example.application.domain.model.Comment;
import com.example.application.domain.ports.in.GetArticleCommentsQuery;
import com.example.application.domain.ports.out.LoadCommentPort;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetArticleCommentsService implements GetArticleCommentsQuery {

  private LoadCommentPort loadCommentPort;

  @Override
  public Collection<Comment> getComments(String slug) {
    return this.loadCommentPort.getComments(slug);
  }
}
