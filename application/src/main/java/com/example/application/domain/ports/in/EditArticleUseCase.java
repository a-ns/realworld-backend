package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

public interface EditArticleUseCase {

  Article editArticle(EditArticleCommand draftArticle) throws ArticleNotFoundException;

  @Data
  @Builder
  class EditArticleCommand {
    @NonNull private Integer id;

    @Nullable private String title;
    @Nullable private String description;
    @Nullable private String body;
  }
}
