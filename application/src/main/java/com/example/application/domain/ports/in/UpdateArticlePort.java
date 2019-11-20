package com.example.application.domain.ports.in;

import com.example.application.domain.model.Article;

public interface UpdateArticlePort {
  Article updateArticle(Article draft);
}
