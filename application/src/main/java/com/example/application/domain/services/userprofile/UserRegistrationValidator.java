package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.RegistrationValidationException;
import com.example.application.domain.model.UserRegistrationCommand;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationValidator {
  private static final Integer PASSWORD_MIN_LENGTH = 5;

  void validate(UserRegistrationCommand registrant) {
    if (registrant.getUsername() == null
        || registrant.getEmail() == null
        || registrant.getPassword() == null
        || registrant.getUsername().isBlank()
        || registrant.getEmail().isBlank()
        || registrant.getPassword().isBlank()
        || registrant.getPassword().length() < PASSWORD_MIN_LENGTH)
      throw new RegistrationValidationException();
  }
}
