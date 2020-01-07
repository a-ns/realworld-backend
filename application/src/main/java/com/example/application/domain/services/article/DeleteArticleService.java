package com.example.application.domain.services.article;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.ports.in.DeleteArticleUseCase;
import com.example.application.domain.ports.out.DeleteArticlePort;
import com.example.application.domain.ports.out.LoadArticlePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DeleteArticleService implements DeleteArticleUseCase {

  private final LoadArticlePort loadArticlePort;
  private final DeleteArticlePort deleteArticlePort;

  @Override
  public Boolean deleteArticleBySlug(String slug) {
    Article article = loadArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);
    deleteArticlePort.deleteArticleById(article.getId());
    return true;
  }
}
