package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.model.User;
import com.example.application.domain.model.UserRegistrationCommand;

public interface RegisterUserUseCase {
  User registerUser(UserRegistrationCommand registrant) throws ExistingUserFoundException;
}
