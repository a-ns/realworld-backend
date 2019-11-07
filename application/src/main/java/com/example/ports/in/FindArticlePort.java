package com.example.ports.in;

import com.example.Article;
import java.util.Optional;

public interface FindArticlePort {
  Optional<Article> findArticle(String slug);

  Optional<Article> findArticleById(Integer id);
}
