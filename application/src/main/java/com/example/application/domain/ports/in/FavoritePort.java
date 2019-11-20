package com.example.application.domain.ports.in;

import com.example.application.domain.model.Article;

public interface FavoritePort {

    Article addFavorite(Integer articleId, Integer userId);
    Article removeFavorite(Integer articleId, Integer userId);
}
