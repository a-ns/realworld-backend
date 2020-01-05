package com.example.application.domain.ports.out;

import com.example.application.domain.model.Article;

public interface UpdateArticlePort {
  Article updateArticle(Article draft);
}
