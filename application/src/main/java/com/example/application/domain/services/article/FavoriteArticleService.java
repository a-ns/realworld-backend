package com.example.application.domain.services.article;

import com.example.application.domain.model.*;
import com.example.application.domain.ports.in.*;
import com.example.application.domain.ports.out.*;
import com.example.application.domain.ports.out.SaveFavoritePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class FavoriteArticleService implements FavoriteArticleUseCase {

  private SaveFavoritePort saveFavoritePort;
  private GetArticleQuery getArticleQuery;
  private GetFavoriteQuery getFavoriteQuery;

  @Override
  public Article favoriteArticle(String slug, User requester) {
    Article found = this.getArticleQuery.getArticle(slug, requester);
    // if favorited don't favorite again, just return article
    if (getFavoriteQuery.hasFavorited(requester.getId(), found.getId())) {
      return this.getArticleQuery.getArticle(slug, requester);
    }
    this.saveFavoritePort.addFavorite(found.getId(), requester.getId());
    return this.getArticleQuery.getArticle(slug, requester);
  }

  @Override
  public Article unfavoriteArticle(String slug, User requester) {
    Article found = this.getArticleQuery.getArticle(slug, requester);
    this.saveFavoritePort.removeFavorite(found.getId(), requester.getId());
    return this.getArticleQuery.getArticle(slug, requester);
  }
}
