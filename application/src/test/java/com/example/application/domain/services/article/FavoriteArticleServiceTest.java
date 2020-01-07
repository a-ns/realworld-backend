package com.example.application.domain.services.article;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.in.GetFavoriteQuery;
import com.example.application.domain.ports.out.SaveFavoritePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Favorite Article Service")
@ExtendWith(MockitoExtension.class)
class FavoriteArticleServiceTest {

  @InjectMocks FavoriteArticleService sut;
  @Mock GetArticleQuery getArticleQuery;
  @Mock GetFavoriteQuery getFavoriteQuery;
  @Mock SaveFavoritePort saveFavoritePort;

  @Test
  @DisplayName("user can favorite an article that they haven't already favorited")
  void favorite_article() {
    // Arrange
    String slug = "slug";
    Integer favoriteCount = 0;
    User requester = User.builder().id(5678).build();

    Article article = Article.builder().id(1234).favoritesCount(favoriteCount).build();
    when(this.getArticleQuery.getArticle(slug, requester)).thenReturn(article);
    when(this.getFavoriteQuery.hasFavorited(requester.getId(), article.getId())).thenReturn(false);

    // Act
    Article actual = sut.favoriteArticle(slug, requester);
    // Assert

    Assertions.assertEquals(true, actual.getFavorited());
    Assertions.assertEquals(favoriteCount + 1, actual.getFavoritesCount());
  }

  @Test
  @DisplayName("user can favorite an article that they already favorited")
  void favorite_article_already_favorited() {
    // Arrange
    String slug = "slug";
    Integer favoriteCount = 1;
    User requester = User.builder().id(5678).build();

    Article article =
        Article.builder().id(1234).favorited(true).favoritesCount(favoriteCount).build();
    when(this.getArticleQuery.getArticle(slug, requester)).thenReturn(article);
    when(this.getFavoriteQuery.hasFavorited(requester.getId(), article.getId())).thenReturn(true);

    // Act
    Article actual = sut.favoriteArticle(slug, requester);
    // Assert

    Assertions.assertEquals(true, actual.getFavorited());
    Assertions.assertEquals(favoriteCount, actual.getFavoritesCount());
  }

  @Test
  @DisplayName("user can unfavorite an article")
  void unfavorite_article() {
    // Arrange
    String slug = "slug";
    Integer favoriteCount = 1;
    User requester = User.builder().id(5678).build();

    Article article = Article.builder().id(1234).favoritesCount(favoriteCount).build();
    when(this.getArticleQuery.getArticle(slug, requester)).thenReturn(article);
    // Act
    Article actual = sut.unfavoriteArticle(slug, requester);
    // Assert

    Assertions.assertEquals(false, actual.getFavorited());
    Assertions.assertEquals(favoriteCount - 1, actual.getFavoritesCount());
  }
}
