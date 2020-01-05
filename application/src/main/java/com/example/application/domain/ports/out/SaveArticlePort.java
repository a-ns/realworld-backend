package com.example.application.domain.ports.out;

import com.example.application.domain.model.Article;

public interface SaveArticlePort {
  Article createArticle(Article article);
}
