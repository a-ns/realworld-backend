package com.example.ports.out;

import com.example.User;

public interface RegisterUserPort {
  User registerUser(String username, String email, String password);
}
