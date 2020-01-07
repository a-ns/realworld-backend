package com.example.application.domain.services.article;

import com.example.application.domain.ports.in.GetFavoriteQuery;
import com.example.application.domain.ports.out.LoadFavoritedPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetFavoriteQueryService implements GetFavoriteQuery {

  private LoadFavoritedPort loadFavoritedPort;

  @Override
  public Boolean hasFavorited(Integer userId, Integer articleId) {
    return this.loadFavoritedPort.isArticleFavoritedBy(articleId, userId);
  }
}
