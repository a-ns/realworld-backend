package com.example;

import com.example.ports.in.GetUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

  private GetUserPort getUserPort;

  public String login(String email, String password) {
    User user = this.getUserPort.getUserByEmail(email);
    assert user != null;
    assert user.getPassword() != null;
    assert user.getPassword().equals(encrypt(password));

    return generateToken(user);
  }

  // put encryption logic here
  private String encrypt(String str) {
    return str;
  }

  // put token gen logic here
  private String generateToken(User user) {
    return "generatedTokenForUser" + user.getEmail();
  }
}
