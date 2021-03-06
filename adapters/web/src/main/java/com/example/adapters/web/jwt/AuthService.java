package com.example.adapters.web.jwt;

import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import javax.security.auth.login.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService implements AuthPort {

  private GetUserPort getUserPort;
  private PasswordEncoder passwordEncoder;
  private JwtService jwtService;

  public String login(String email, String password) throws LoginException {
    User user = this.getUserPort.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    assert user != null;
    assert user.getPassword() != null;
    if (!matches(password, user.getPassword())) {
      throw new LoginException();
    }

    return generateToken(user);
  }

  public User decrypt(String token) {
    String username = jwtService.getSubFromToken(token).orElseThrow();
    User user = getUserPort.getUserByUsername(username).get();
    return user;
  }

  private Boolean matches(String a, String b) {
    return passwordEncoder.matches(a, b);
  }

  public String encrypt(String str) {
    return passwordEncoder.encode(str);
  }

  public String generateToken(User user) {
    return jwtService.toToken(user);
  }
}
