package com.example.ports.in;

import com.example.User;
import java.util.Optional;

public interface GetUserPort {
  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);
}
