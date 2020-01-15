package com.example.application.domain.services.article;

import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.Profile;
import com.example.application.domain.ports.in.PublishArticleUseCase;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.SaveArticlePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PublishArticleService implements PublishArticleUseCase {
  private final LoadArticlePort loadArticlePort;
  private final SaveArticlePort saveArticlePort;
  private final SlugMaker slugMaker;

  @Override
  public Article publishArticle(PublishArticleCommand articleToPublish) {
    assert articleToPublish != null;
    assert articleToPublish.getBody() != null;
    assert articleToPublish.getDescription() != null;
    assert articleToPublish.getTitle() != null;
    assert articleToPublish.getPublisher() != null;
    loadArticlePort
        .findArticle(slugMaker.createSlug(articleToPublish.getTitle()))
        .ifPresent(
            (article) -> {
              throw new ArticleAlreadyExistsException();
            });
    Article article =
        Article.builder()
            .author(
                Profile.builder().username(articleToPublish.getPublisher().getUsername()).build())
            .body(articleToPublish.getBody())
            .description(articleToPublish.getDescription())
            .slug(slugMaker.createSlug(articleToPublish.getTitle()))
            .tags(articleToPublish.getTagList())
            .title(articleToPublish.getTitle())
            .favorited(false)
            .favoritesCount(0)
            .build();
    Article created = saveArticlePort.createArticle(article);
    return created;
  }
}
