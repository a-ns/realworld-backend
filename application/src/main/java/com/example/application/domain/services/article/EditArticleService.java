package com.example.application.domain.services.article;

import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.ports.in.EditArticleUseCase;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.UpdateArticlePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class EditArticleService implements EditArticleUseCase {

  private final LoadArticlePort loadArticlePort;
  private final UpdateArticlePort updateArticlePort;
  private final SlugMaker slugMaker;

  @Override
  public Article editArticle(EditArticleCommand draftArticle) {
    assert draftArticle.getBody() != null;
    assert draftArticle.getDescription() != null;
    assert draftArticle.getTitle() != null;

    Article article =
        this.loadArticlePort
            .findArticleById(draftArticle.getId())
            .orElseThrow(ArticleNotFoundException::new);

    if (!"".equals(draftArticle.getTitle())) {
      article.setTitle(draftArticle.getTitle());
      String newSlug = this.slugMaker.createSlug(draftArticle.getTitle());
      loadArticlePort
          .findArticle(newSlug)
          .ifPresent(
              (collision) -> {
                throw new ArticleAlreadyExistsException();
              });
      article.setSlug(newSlug);
    }
    if (!"".equals(draftArticle.getDescription())) {
      article.setDescription(draftArticle.getDescription());
    }
    if (!"".equals(draftArticle.getBody())) {
      article.setBody(draftArticle.getBody());
    }

    return this.updateArticlePort.updateArticle(article);
  }
}
