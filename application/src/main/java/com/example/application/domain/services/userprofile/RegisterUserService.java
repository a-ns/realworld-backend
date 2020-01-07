package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.RegisterUserUseCase;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import com.example.application.domain.ports.out.SaveUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class RegisterUserService implements RegisterUserUseCase {

  private final AuthPort authService;
  private final GetUserPort getUserPort;
  private final SaveUserPort saveUserPort;

  public User registerUser(String username, String email, String password)
      throws ExistingUserFoundException {
    assert username != null;
    assert email != null;
    assert password != null;
    assert !username.isBlank();
    assert !email.isBlank();
    assert !password.isBlank();
    assert password.length() > 5;
    this.getUserPort
        .getUserByUsername(username)
        .ifPresent(
            (existing) -> {
              throw new UsernameAlreadyTakenException();
            });
    this.getUserPort
        .getUserByEmail(email)
        .ifPresent(
            (existing) -> {
              throw new EmailAreadyTakenException();
            });
    User user = this.saveUserPort.registerUser(username, email, authService.encrypt(password));
    String token = this.authService.generateToken(user);
    user.setToken(token);
    return user;
  }
}
