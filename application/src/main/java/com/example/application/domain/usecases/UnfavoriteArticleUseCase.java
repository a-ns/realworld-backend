package com.example.application.domain.usecases;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;

public interface UnfavoriteArticleUseCase {
    Article unfavoriteArticle(String slug, User requester);
}
