package com.example.application.domain.ports.out;

import com.example.application.domain.model.Article;

public interface SaveFavoritePort {

  Article addFavorite(Integer articleId, Integer userId);

  Article removeFavorite(Integer articleId, Integer userId);
}
