package com.example.application.domain.services.article;

import com.example.application.domain.ports.in.GetFavoriteQuery;
import com.example.application.domain.ports.out.LoadArticleFavoritedPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetFavoriteQueryService implements GetFavoriteQuery {

  private LoadArticleFavoritedPort loadArticleFavoritedPort;

  @Override
  public Boolean hasFavorited(Integer userId, Integer articleId) {
    return this.loadArticleFavoritedPort.isArticleFavoritedBy(articleId, userId);
  }
}
