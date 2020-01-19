package com.example.adapters.web.dto.mapper;

import com.example.adapters.web.dto.input.UserUpdatePayload;
import com.example.adapters.web.dto.output.GetUserResponse;
import com.example.application.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
  public GetUserResponse mapUserToResponse(User user) {
    return GetUserResponse.builder()
        .user(
            GetUserResponse.User.builder()
                .bio(user.getBio())
                .email(user.getEmail())
                .image(user.getImage())
                .username(user.getUsername())
                .token(user.getToken())
                .build())
        .build();
  }

  public User mapResponseToUser(UserUpdatePayload res) {
    return User.builder()
        .username(res.getUser().getUsername())
        .image(res.getUser().getImage())
        .email(res.getUser().getEmail())
        .bio(res.getUser().getBio())
        .password(res.getUser().getPassword())
        .build();
  }
}
