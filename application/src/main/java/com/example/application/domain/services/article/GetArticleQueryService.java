package com.example.application.domain.services.article;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetArticleQueryService implements GetArticleQuery {

  private LoadArticlePort loadArticlePort;
  private GetProfileQuery getProfileQuery;
  private LoadFavoritedPort loadFavoritedPort;
  private LoadArticleFavoriteCountPort loadArticleFavoriteCountPort;

  @Override
  public Article getArticle(String slug, Optional<User> requester) {

    Article article =
        this.loadArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);

    Profile profile = getProfileQuery.getProfile(article.getAuthor().getUsername(), requester);
    if (requester.isPresent()) {
      Boolean isFavorited =
          this.loadFavoritedPort.isArticleFavoritedBy(article.getId(), requester.get().getId());
      article.setFavorited(isFavorited);
      Integer favoritesCount = this.loadArticleFavoriteCountPort.getFavoriteCount(article.getId());
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
}
