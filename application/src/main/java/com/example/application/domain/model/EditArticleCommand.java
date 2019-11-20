package com.example.application.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
public class EditArticleCommand {
  @NonNull private Integer id;
  @Nullable private String title;
  @Nullable private String description;
  @Nullable private String body;
}
