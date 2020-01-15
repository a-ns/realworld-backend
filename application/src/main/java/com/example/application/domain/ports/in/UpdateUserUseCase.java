package com.example.application.domain.ports.in;

import com.example.application.domain.model.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface UpdateUserUseCase {
  User updateUser(UpdateUserCommand input);

  @Data
  @Builder
  class UpdateUserCommand {
    @NonNull private Integer user;

    @Nullable private String email;
    @Nullable private String username;
    @Nullable private String password;
    @Nullable private String image;
    @Nullable private String bio;
  }
}
