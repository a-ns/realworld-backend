package com.example.application.domain.ports.in;

public interface GetFavoriteQuery {
  Boolean hasFavorited(Integer userId, Integer articleId);
}
