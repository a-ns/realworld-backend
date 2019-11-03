package com.example.web.dto;

import com.example.User;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
  public UserResponse mapUserToResponse(User user) {
    return UserResponse.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .username(user.getUsername())
        .token(user.getToken())
        .build();
  }
}
