package com.example.ports.out;

import com.example.User;
import com.example.exceptions.ExistingUserFoundException;

public interface RegisterUserPort {
  User registerUser(String username, String email, String password)
      throws ExistingUserFoundException;
}
