package com.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {

  private Body user;

  @Data
  @Builder
  public static class Body {
    private String username;
    private String email;
    private String password;
  }
}
