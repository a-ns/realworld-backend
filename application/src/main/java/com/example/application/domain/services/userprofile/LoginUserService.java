package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.LoginUserUseCase;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class LoginUserService implements LoginUserUseCase {

  private final AuthPort authService;

  private final GetUserPort getUserPort;

  public User login(String email, String password) throws FailedLoginException {
    User user = this.getUserPort.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    String token;
    try {
      token = this.authService.login(email, password);
    } catch (LoginException e) {
      throw new FailedLoginException();
    }
    user.setToken(token);
    return user;
  }
}
