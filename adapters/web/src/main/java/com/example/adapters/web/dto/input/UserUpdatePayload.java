package com.example.adapters.web.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdatePayload {

  private User user;

  @AllArgsConstructor
  @Data
  @NoArgsConstructor
  @Builder
  public static class User {
    @Nullable private String email;
    @Nullable private String password;
    @Nullable private String username;
    @Nullable private String bio;
    @Nullable private String image;
  }
}
