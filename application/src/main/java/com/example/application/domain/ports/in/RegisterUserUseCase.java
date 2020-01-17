package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.SelfValidating;
import com.example.application.domain.model.User;
import javax.validation.constraints.NotNull;
import lombok.Value;

public interface RegisterUserUseCase {

  User registerUser(UserRegistrationCommand registrant) throws UsernameAlreadyTakenException, EmailAreadyTakenException;

  @Value
  class UserRegistrationCommand extends SelfValidating<UserRegistrationCommand> {
    @NotNull private String username;
    @NotNull private String email;
    @NotNull private String password;

    public UserRegistrationCommand(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
      this.validateSelf();
    }
  }
}
