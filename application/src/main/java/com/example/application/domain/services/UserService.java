package com.example.application.domain.services;

import com.example.application.domain.FollowUserUseCase;
import com.example.application.domain.LoginUserUseCase;
import com.example.application.domain.RegisterUserUseCase;
import com.example.application.domain.exceptions.EmailAreadyTakenException;
import com.example.application.domain.exceptions.ExistingUserFoundException;
import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.exceptions.UsernameAlreadyTakenException;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.FollowUserPort;
import com.example.application.domain.ports.in.LoadProfilePort;
import com.example.application.domain.ports.in.RegisterUserPort;
import com.example.application.domain.ports.in.UpdateUserPort;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetProfileQuery;
import com.example.application.domain.ports.out.GetUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Component
@Transactional
class UserService implements FollowUserUseCase, GetProfileQuery, RegisterUserUseCase, LoginUserUseCase {

  private LoadProfilePort profilePort;
  private final GetUserPort getUserPort;
  private final RegisterUserPort registerUserPort;
  private final AuthPort authService;
  private final UpdateUserPort updateUserPort;
  private final FollowUserPort followUserPort;


  @Override
  public Profile getProfile(String username, Optional<User> request) {
    Boolean isFollowing = profilePort.isFollowing(username, request.orElse(null));
    Profile p = profilePort.loadProfile(username);
    p.setFollowing(isFollowing);
    return p;
  }


  public enum SearchType {
    USERNAME,
    EMAIL
  }



  @Override
  public Profile follow(User followed, User follower) {
    return this.followUserPort.saveFollowRelation(followed, follower);
  }

  @Override
  public Profile unfollow(User followed, User follower) {
    return this.followUserPort.removeFollowRelation(followed, follower);
  }

  public User registerUser(String username, String email, String password) throws ExistingUserFoundException {
    assert username != null;
    assert email != null;
    assert password != null;
    assert !username.isBlank();
    assert !email.isBlank();
    assert !password.isBlank();
    assert password.length() > 5;
    this.getUserPort.getUserByUsername(username).ifPresent((existing) -> {throw new UsernameAlreadyTakenException();});
    this.getUserPort.getUserByEmail(email).ifPresent((existing) -> {throw new EmailAreadyTakenException();});
    User user = this.registerUserPort.registerUser(username, email, authService.encrypt(password));
    String token = this.authService.generateToken(user);
    user.setToken(token);
    return user;
  }

  public Optional<User> getUser(String value, SearchType searchType) {
    switch (searchType) {
      case USERNAME:
        return getUserPort.getUserByUsername(value);
      case EMAIL:
        return getUserPort.getUserByEmail(value);
      default:
        throw new IllegalArgumentException();
    }
  }

  public User login(String email, String password) throws FailedLoginException {
    String token;
    try {
      token = this.authService.login(email, password);
    } catch (LoginException e) {
      throw new FailedLoginException();
    }
    User user = this.getUserPort.getUserByEmail(email).orElseThrow(FailedLoginException::new);
    user.setToken(token);
    return user;
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
