package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.User;
import java.util.List;
import lombok.*;
import org.springframework.lang.Nullable;

public interface PublishArticleUseCase {

  Article publishArticle(PublishArticleCommand article) throws ArticleAlreadyExistsException;

  @Data
  @NoArgsConstructor
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  class PublishArticleCommand {
    @NonNull private String title;
    @NonNull private String description;
    @NonNull private String body;
    @Nullable private List<String> tagList;
    @NonNull private User publisher;
  }
}
