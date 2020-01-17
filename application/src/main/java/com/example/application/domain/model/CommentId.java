package com.example.application.domain.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentId {
  private UUID id;
}
