package com.example.web.dto;

import com.example.User;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
  public UserResponse mapUserToResponse(User user) {
    return UserResponse.builder()
        .user(
            UserResponse.User.builder()
                .bio(user.getBio())
                .email(user.getEmail())
                .image(user.getImage())
                .username(user.getUsername())
                .token(user.getToken())
                .build())
        .build();
  }

  public User mapResponseToUser(UserUpdateRequest res) {
    return User.builder()
        .username(res.getUser().getUsername())
        .image(res.getUser().getImage())
        .email(res.getUser().getEmail())
        .bio(res.getUser().getBio())
        .password(res.getUser().getPassword())
        .build();
  }
}
