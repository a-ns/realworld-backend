package com.example.application.domain.usecases;

public interface FindFavoriteUseCase {
    Boolean hasFavorited(Integer userId, Integer articleId);
}
