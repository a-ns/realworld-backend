package com.example;

import com.example.exceptions.ArticleAlreadyExistsException;
import com.example.exceptions.ArticleNotFoundException;
import com.example.ports.in.FindArticlePort;
import com.example.ports.out.CreateArticlePort;
import com.example.ports.out.DeleteArticlePort;
import com.example.ports.out.UpdateArticlePort;
import com.example.usecases.DeleteArticleUseCase;
import com.example.usecases.EditArticleUseCase;
import com.example.usecases.PublishArticleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ArticleService implements PublishArticleUseCase, EditArticleUseCase, DeleteArticleUseCase {

  private CreateArticlePort createArticlePort;
  private FindArticlePort findArticlePort;
  private UpdateArticlePort updateArticlePort;
  private DeleteArticlePort deleteArticlePort;

  @Override
  public Article publishArticle(PublishArticleCommand articleToPublish) {
    assert articleToPublish != null;
    assert articleToPublish.getBody() != null;
    assert articleToPublish.getDescription() != null;
    assert articleToPublish.getTitle() != null;
    findArticlePort
        .findArticle(createSlug(articleToPublish.getTitle()))
        .ifPresent(
            (article) -> {
              throw new ArticleAlreadyExistsException();
            });
    Article article =
        Article.builder()
            .author(
                Profile.builder().username(articleToPublish.getPublisher().getUsername()).build())
            .body(articleToPublish.getBody())
            .description(articleToPublish.getDescription())
            .favorited(false)
            .favoritesCount(0)
            .slug(createSlug(articleToPublish.getTitle()))
            .tags(articleToPublish.getTagList())
            .title(articleToPublish.getTitle())
            .build();
    return createArticlePort.createArticle(article);
  }

  @Override
  public Article editArticle(EditArticleCommand draftArticle) {
    assert (draftArticle.getBody() != null
            && draftArticle.getDescription() != null
            && draftArticle.getTitle() != null)
        == false;
    assert !draftArticle.getTitle().isBlank();
    Article article =
        findArticlePort
            .findArticleById(draftArticle.getId())
            .orElseThrow(ArticleNotFoundException::new);

    if (!"".equals(draftArticle.getTitle())) {
      article.setTitle(draftArticle.getTitle());
      article.setSlug(createSlug(draftArticle.getTitle()));
    }
    if (!"".equals(draftArticle.getDescription())) {
      article.setDescription(draftArticle.getDescription());
    }
    if (!"".equals(draftArticle.getBody())) {
      article.setBody(draftArticle.getBody());
    }

    return updateArticlePort.updateArticle(article);
  }

  private String createSlug(String title) {
    return title.replace("\\s+", "-");
  }

  @Override
  public Boolean deleteArticleBySlug(String slug) {
    Article article = findArticlePort.findArticle(slug).orElseThrow(ArticleNotFoundException::new);
    deleteArticlePort.deleteArticleById(article.getId());
    return true;
  }
}
