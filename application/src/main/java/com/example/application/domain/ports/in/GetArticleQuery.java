package com.example.application.domain.ports.in;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface GetArticleQuery {

  Article getArticle(String slug, User requester);

  Article getArticle(String slug);

  List<Article> getRecentArticles(
      Optional<String> tag,
      Optional<String> author,
      Optional<String> favorited,
      Optional<Integer> limit,
      Optional<Integer> offset,
      Optional<User> user);
}
