package com.example.adapters.web.dto;

import java.util.List;
import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DraftArticle {

  private Body article;

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Body {
    @NotEmpty private String title;
    @NotEmpty private String description;
    @NotEmpty private String body;
    @Nullable private List<String> tagList;
  }
}
