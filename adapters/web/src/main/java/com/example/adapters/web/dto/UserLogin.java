package com.example.adapters.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserLogin {

  private Body user;

  @Builder
  @Data
  public static class Body {

    private String email;
    private String password;
  }
}
