package com.example.ports.out;

import com.example.Article;

public interface DeleteArticlePort {
  Article deleteArticleById(Integer id);
}
