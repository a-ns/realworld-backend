package com.example.application.domain.ports.out;

import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.model.User;

public interface SaveUserPort {
  User registerUser(String username, String email, String password)
      throws ExistingUserFoundException;
}
