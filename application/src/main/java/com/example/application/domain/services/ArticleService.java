package com.example.application.domain.services;

import com.example.application.domain.*;
import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.*;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.*;
import com.example.application.domain.ports.out.DeleteArticlePort;
import com.example.application.domain.ports.out.SaveArticlePort;
import com.example.application.domain.ports.out.SaveFavoritePort;
import com.example.application.domain.ports.out.UpdateArticlePort;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ArticleService
    implements PublishArticleUseCase,
        EditArticleUseCase,
        DeleteArticleUseCase,
        GetArticleQuery,
        FavoriteArticleUseCase,
        FindFavoriteUseCase {

  private SaveArticlePort saveArticlePort;
  private FindArticlePort findArticlePort;
  private UpdateArticlePort updateArticlePort;
  private DeleteArticlePort deleteArticlePort;
  private GetProfileQuery getProfileQuery;
  private IsFavoritedPort isFavoritedPort;
  private ArticleFavoriteCountPort articleFavoriteCountPort;
  private SaveFavoritePort saveFavoritePort;

  @Override
  public Article publishArticle(PublishArticleCommand articleToPublish) {
    assert articleToPublish != null;
    assert articleToPublish.getBody() != null;
    assert articleToPublish.getDescription() != null;
    assert articleToPublish.getTitle() != null;
    findArticlePort
        .findArticle(createSlug(articleToPublish.getTitle()))
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
            .slug(createSlug(articleToPublish.getTitle()))
            .tags(articleToPublish.getTagList())
            .title(articleToPublish.getTitle())
            .build();
    Article created = saveArticlePort.createArticle(article);
    created.setFavoritesCount(0);
    created.setFavorited(false);
    return created;
  }

  @Override
  public Article editArticle(EditArticleCommand draftArticle) {
    assert (draftArticle.getBody() != null
            && draftArticle.getDescription() != null
            && draftArticle.getTitle() != null)
        == false;
    assert !draftArticle.getTitle().isBlank();
    Article article =
        findArticlePort
            .findArticleById(draftArticle.getId())
            .orElseThrow(ArticleNotFoundException::new);

    if (!"".equals(draftArticle.getTitle())) {
      article.setTitle(draftArticle.getTitle());
      article.setSlug(createSlug(draftArticle.getTitle()));
    }
    if (!"".equals(draftArticle.getDescription())) {
      article.setDescription(draftArticle.getDescription());
    }
    if (!"".equals(draftArticle.getBody())) {
      article.setBody(draftArticle.getBody());
    }

    return updateArticlePort.updateArticle(article);
  }

  private String createSlug(String title) {
    return title.replaceAll("\\s+", "-");
  }

  @Override
  public Boolean deleteArticleBySlug(String slug) {
    Article article = findArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);
    deleteArticlePort.deleteArticleById(article.getId());
    return true;
  }

  @Override
  public Article getArticle(String slug, Optional<User> requester) {

    Article article =
        this.findArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);

    Profile profile = getProfileQuery.getProfile(article.getAuthor().getUsername(), requester);
    if (requester.isPresent()) {
      Boolean isFavorited =
          this.isFavoritedPort.isArticleFavoritedBy(article.getId(), requester.get().getId());
      article.setFavorited(isFavorited);
      Integer favoritesCount = this.articleFavoriteCountPort.getFavoriteCount(article.getId());
      article.setFavoritesCount(favoritesCount);
    }
    article.setAuthor(profile);
    return article;
  }

  @Override
  public List<Article> getRecentArticles(
      Optional<List<String>> tags,
      Optional<String> author,
      Optional<Boolean> favorited,
      Optional<User> requester) {
    if (favorited.isPresent() && favorited.get() && requester.isEmpty()) {
      return Collections.emptyList();
    }
    return null;
  }

  @Override
  public Article favoriteArticle(String slug, User requester) {
    Article found = this.getArticle(slug, Optional.of(requester));
    // if favorited don't favorite again, just return article
    if (hasFavorited(requester.getId(), found.getId())) {
      return this.getArticle(slug, Optional.of(requester));
    }
    this.saveFavoritePort.addFavorite(found.getId(), requester.getId());
    return this.getArticle(slug, Optional.of(requester));
  }

  @Override
  public Article unfavoriteArticle(String slug, User requester) {
    Article found = this.getArticle(slug, Optional.of(requester));
    this.saveFavoritePort.removeFavorite(found.getId(), requester.getId());
    return this.getArticle(slug, Optional.of(requester));
  }

  @Override
  public Boolean hasFavorited(Integer userId, Integer articleId) {
    return isFavoritedPort.isArticleFavoritedBy(articleId, userId);
  }
}
