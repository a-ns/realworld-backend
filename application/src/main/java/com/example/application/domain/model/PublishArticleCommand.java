package com.example.application.domain.model;

import java.util.List;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class PublishArticleCommand {
  @NonNull private String title;
  @NonNull private String description;
  @NonNull private String body;
  @Nullable private List<String> tagList;
  @NonNull private User publisher;
}
