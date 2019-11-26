package com.example.application.domain;

public interface FindFavoriteUseCase {
    Boolean hasFavorited(Integer userId, Integer articleId);
}
