package com.example.application.domain.ports.in;

import com.example.application.domain.model.User;

public interface UpdateUserUseCase {
  User updateUser(String username, User user);
}
