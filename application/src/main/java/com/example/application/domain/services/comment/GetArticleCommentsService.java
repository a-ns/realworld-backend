package com.example.application.domain.services.comment;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.GetArticleCommentsQuery;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.LoadCommentPort;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetArticleCommentsService implements GetArticleCommentsQuery {

  private LoadCommentPort loadCommentPort;
  private GetProfileQuery getProfileQuery;

  @Override
  public Collection<Comment> getComments(String slug) {
    return this.loadCommentPort.getComments(slug);
  }

  @Override
  public Collection<Comment> getComments(String slug, User requester) {
    var comments = this.getComments(slug);
    comments.forEach(
        comment -> {
          comment.setAuthor(
              getProfileQuery.getProfile(
                  comment.getAuthor().getUsername(), Optional.of(requester)));
        });
    return comments;
  }
}
