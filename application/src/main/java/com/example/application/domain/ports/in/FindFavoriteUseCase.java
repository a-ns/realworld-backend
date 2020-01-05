package com.example.application.domain.ports.in;

public interface FindFavoriteUseCase {
  Boolean hasFavorited(Integer userId, Integer articleId);
}
