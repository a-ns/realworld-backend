package com.example.application.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationCommand {
  private String username;
  private String email;
  private String password;
}
