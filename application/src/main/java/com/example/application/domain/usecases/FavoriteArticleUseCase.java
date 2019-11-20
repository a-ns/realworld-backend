package com.example.application.domain.usecases;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;

public interface FavoriteArticleUseCase {
    Article favoriteArticle(String slug, User requester);
}
