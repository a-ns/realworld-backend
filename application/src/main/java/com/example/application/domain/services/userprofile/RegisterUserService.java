package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.exceptions.RegistrationValidationException;
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

  private static final Integer PASSWORD_MIN_LENGTH = 5;

  public User registerUser(String username, String email, String password)
      throws ExistingUserFoundException {
    if (username == null
        || email == null
        || password == null
        || username.isBlank()
        || email.isBlank()
        || password.isBlank()
        || password.length() < PASSWORD_MIN_LENGTH) throw new RegistrationValidationException();
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
    User user =
        User.builder()
            .username(username)
            .email(email)
            .password(authService.encrypt(password))
            .build();
    user = this.saveUserPort.saveUser(user);
    String token = this.authService.generateToken(user);
    user.setToken(token);
    return user;
  }
}
