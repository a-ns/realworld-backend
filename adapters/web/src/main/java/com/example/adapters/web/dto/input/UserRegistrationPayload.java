package com.example.adapters.web.dto.input;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationPayload implements Serializable {

  private Body user;

  @Data
  @Builder
  public static class Body {
    private String username;
    private String email;
    private String password;
  }
}
