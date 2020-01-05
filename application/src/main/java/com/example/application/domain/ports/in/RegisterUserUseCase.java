package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.model.User;

public interface RegisterUserUseCase {
  User registerUser(String username, String email, String password)
      throws ExistingUserFoundException;
}
