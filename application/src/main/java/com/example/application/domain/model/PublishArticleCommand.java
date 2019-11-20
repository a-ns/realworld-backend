package com.example.application.domain.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
public class PublishArticleCommand {
  @NonNull private String title;
  @NonNull private String description;
  @NonNull private String body;
  @Nullable private List<String> tagList;
  @NonNull private User publisher;
}
