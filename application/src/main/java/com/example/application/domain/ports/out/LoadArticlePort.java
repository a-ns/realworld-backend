package com.example.application.domain.ports.out;

import com.example.application.domain.model.Article;
import java.util.Optional;

public interface LoadArticlePort {
  Optional<Article> findArticle(String slug);

  Optional<Article> findArticleById(Integer id);
}
