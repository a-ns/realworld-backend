package com.example.adapters.web.dto.input;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserLoginPayload implements Serializable {

  private Body user;

  @Builder
  @Data
  public static class Body {

    private String email;
    private String password;
  }
}
