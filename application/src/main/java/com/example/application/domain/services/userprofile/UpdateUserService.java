package com.example.application.domain.services.userprofile;

import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.*;
import com.example.application.domain.ports.out.*;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Transactional
class UpdateUserService implements UpdateUserUseCase {

  private final GetUserPort getUserPort;
  private final AuthPort authService;
  private final UpdateUserPort updateUserPort;

  public enum SearchType {
    USERNAME,
    EMAIL
  }

  private Optional<User> getUser(String value, SearchType searchType) {
    switch (searchType) {
      case USERNAME:
        return getUserPort.getUserByUsername(value);
      case EMAIL:
        return getUserPort.getUserByEmail(value);
      default:
        throw new IllegalArgumentException();
    }
  }

  public User updateUser(String previousUsername, User updatePayload) {

    User existing =
        getUserPort.getUserByUsername(previousUsername).orElseThrow(UserNotFoundException::new);

    if (updatePayload.getUsername() != null && !updatePayload.getUsername().isBlank()) {

      Optional<User> collidingUsername = getUser(updatePayload.getUsername(), SearchType.USERNAME);
      if (collidingUsername.isPresent()) {
        throw new UsernameAlreadyTakenException();
      }
      existing.setUsername(updatePayload.getUsername());
    }
    if (updatePayload.getEmail() != null && isValidEmail(updatePayload.getEmail())) {
      Optional<User> collingEmail = getUser(updatePayload.getEmail(), SearchType.EMAIL);
      if (collingEmail.isPresent()) {
        throw new EmailAreadyTakenException();
      }
      existing.setEmail(updatePayload.getEmail());
    }
    if (updatePayload.getPassword() != null && !updatePayload.getPassword().isBlank()) {
      existing.setPassword(authService.encrypt(updatePayload.getPassword()));
    }
    if (!"".equals(updatePayload.getImage())) {
      existing.setImage(updatePayload.getImage());
    }
    if (updatePayload.getBio() != null) {
      existing.setBio(updatePayload.getBio());
    }

    return this.updateUserPort.save(existing.getId(), existing);
  }

  private Boolean isValidEmail(String email) {
    return true; // TODO implement later
  }
}
