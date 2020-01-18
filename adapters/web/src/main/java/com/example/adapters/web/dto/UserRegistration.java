package com.example.adapters.web.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration implements Serializable {

  private Body user;

  @Data
  @Builder
  public static class Body {
    private String username;
    private String email;
    private String password;
  }
}
