package com.example.ports.in;

import com.example.User;

public interface GetUserPort {
  User getUserByUsername(String username);

  User getUserByEmail(String email);
}
