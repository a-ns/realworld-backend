package com.example.application.domain.services.article;

import static org.junit.jupiter.api.Assertions.*;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.LoadArticleFavoriteCountPort;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.LoadFavoritedPort;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get Article Query Service")
class GetArticleQueryServiceTest {

  @InjectMocks GetArticleQueryService sut;
  @Mock private LoadArticlePort loadArticlePort;
  @Mock private GetProfileQuery getProfileQuery;
  @Mock private LoadFavoritedPort loadFavoritedPort;
  @Mock private LoadArticleFavoriteCountPort loadArticleFavoriteCountPort;

  @Test
  @DisplayName("Get an article without a requester should return that the article is not favorited")
  void not_favorited_if_no_requester_present() {
    // Arrange
    String slug = "slug";
    String authorName = "author";
    Profile author = Profile.builder().username(authorName).build();
    Article article = Article.builder().author(author).id(1234).build();

    Mockito.when(this.loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    Mockito.when(this.getProfileQuery.getProfile(authorName, Optional.empty())).thenReturn(author);
    Mockito.when(this.loadArticleFavoriteCountPort.getFavoriteCount(Mockito.anyInt()))
        .thenReturn(0);
    // Act
    Article actual = this.sut.getArticle(slug);
    // Assert

    Assertions.assertEquals(article.getSlug(), actual.getSlug());
    Assertions.assertEquals(false, actual.getFavorited());
  }

  @Test
  @DisplayName("the found article should be favorited if the requester favorited it")
  void should_be_favorited_if_the_user_previously_favorited_the_article() {
    // Arrange
    String slug = "slug";
    String authorName = "author";
    Integer userId = 1111;
    Integer articleId = 1234;
    Profile author = Profile.builder().username(authorName).build();
    Article article = Article.builder().author(author).id(articleId).build();

    User requester = User.builder().id(userId).build();
    Mockito.when(this.loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    Mockito.when(this.getProfileQuery.getProfile(authorName, Optional.of(requester)))
        .thenReturn(author);
    Mockito.when(this.loadArticleFavoriteCountPort.getFavoriteCount(Mockito.anyInt()))
        .thenReturn(1);
    Mockito.when(this.loadFavoritedPort.isArticleFavoritedBy(articleId, userId)).thenReturn(true);
    // Act
    Article actual = this.sut.getArticle(slug, requester);
    // Assert

    Assertions.assertEquals(article.getSlug(), actual.getSlug());
    Assertions.assertEquals(true, actual.getFavorited());
  }
}
