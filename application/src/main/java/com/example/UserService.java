package com.example;

import com.example.ports.in.GetUserPort;
import com.example.ports.out.RegisterUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserService {
  public enum SearchType {
    USERNAME,
    EMAIL
  }

  private final GetUserPort getUserPort;
  private final RegisterUserPort registerUserPort;
  private final AuthService authService;

  public User register(String username, String email, String password) {
    assert username != null;
    assert email != null;
    assert password != null;
    assert !username.isBlank();
    assert !email.isBlank();
    assert !password.isBlank();
    assert password.length() > 5;
    User user = this.registerUserPort.registerUser(username, email, password);
    String token = this.authService.login(user.getEmail(), user.getPassword());
    user.setToken(token);
    return user;
  }

  public User getUser(String value, SearchType searchType) {
    switch (searchType) {
      case USERNAME:
        return getUserPort.getUserByUsername(value);
      case EMAIL:
        return getUserPort.getUserByEmail(value);
      default:
        throw new IllegalArgumentException();
    }
  }

  public User login(String email, String password) {
    String token = this.authService.login(email, password);
    User user = this.getUserPort.getUserByEmail(email);
    user.setToken(token);
    return user;
  }
}
