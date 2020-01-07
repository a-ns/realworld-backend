package com.example.application.domain.services.article;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.ports.out.DeleteArticlePort;
import com.example.application.domain.ports.out.LoadArticlePort;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteArticleServiceTest {
  @InjectMocks DeleteArticleService sut;
  @Mock LoadArticlePort loadArticlePort;
  @Mock DeleteArticlePort deleteArticlePort;

  @Test
  @DisplayName("deletes an article")
  void delete_article() {
    // Arrange
    String slug = "slug";
    Article article = Article.builder().slug(slug).id(1234).build();
    when(loadArticlePort.findArticle(slug)).thenReturn(Optional.of(article));
    // Act
    Boolean actual = sut.deleteArticleBySlug(slug);
    // Assert
    verify(deleteArticlePort).deleteArticleById(article.getId());
    Assertions.assertEquals(true, actual);
  }

  @Test
  @DisplayName("article must exist in order to be deleted")
  void article_must_exist() {
    // Arrange
    String slug = "slug";
    when(loadArticlePort.findArticle(slug)).thenReturn(Optional.empty());
    // Act
    Assertions.assertThrows(ArticleNotFoundException.class, () -> sut.deleteArticleBySlug(slug));
  }
}
