package com.example.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserToken {
  private String username;
  private String email;
}
