package com.example.application.domain.services.article;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.EditArticleCommand;
import com.example.application.domain.ports.in.EditArticleUseCase;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.UpdateArticlePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EditArticleService implements EditArticleUseCase {

  private LoadArticlePort loadArticlePort;
  private UpdateArticlePort updateArticlePort;
  private SlugMaker slugMaker;

  @Override
  public Article editArticle(EditArticleCommand draftArticle) {
    assert (draftArticle.getBody() != null
            && draftArticle.getDescription() != null
            && draftArticle.getTitle() != null)
        == false;
    assert !draftArticle.getTitle().isBlank();
    Article article =
        this.loadArticlePort
            .findArticleById(draftArticle.getId())
            .orElseThrow(ArticleNotFoundException::new);

    if (!"".equals(draftArticle.getTitle())) {
      article.setTitle(draftArticle.getTitle());
      article.setSlug(this.slugMaker.createSlug(draftArticle.getTitle()));
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
