package com.example.application.domain.services.comment;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.model.Profile;
import com.example.application.domain.ports.in.CommentOnArticleUseCase;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.SaveCommentPort;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class CommentOnArticleService implements CommentOnArticleUseCase {

  private GetProfileQuery getProfileQuery;
  private SaveCommentPort saveCommentPort;
  private LoadArticlePort loadArticlePort;

  @Override
  public Comment publishComment(PublishCommentCommand input) {

    Profile author =
        getProfileQuery.getProfile(input.getCommentAuthor().getUsername(), Optional.empty());
    Article article =
        loadArticlePort
            .findArticle(input.getArticleSlug())
            .orElseThrow(ArticleNotFoundException::new);
    Comment newComment =
        Comment.builder()
            .body(input.getBody())
            .author(author)
            .id(CommentId.builder().id(UUID.randomUUID()).build())
            .articleId(article.getId())
            .build();
    return this.saveCommentPort.save(newComment);
  }
}
