package com.example.application.domain.ports.in;

import com.example.application.domain.model.User;
import com.example.application.domain.exceptions.ExistingUserFoundException;

public interface RegisterUserPort {
  User registerUser(String username, String email, String password)
      throws ExistingUserFoundException;
}
