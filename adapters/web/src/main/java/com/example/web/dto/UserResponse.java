package com.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserResponse {
  private String email;
  private String token;
  private String username;
  private String bio;
  private String image;
}