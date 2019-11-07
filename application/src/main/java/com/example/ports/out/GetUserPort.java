package com.example.ports.out;

import com.example.User;
import java.util.Optional;

public interface GetUserPort {
  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);
}
