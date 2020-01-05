package com.example.application.domain.ports.in;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;

public interface FavoriteArticleUseCase {
  Article favoriteArticle(String slug, User requester);

  Article unfavoriteArticle(String slug, User requester);
}
