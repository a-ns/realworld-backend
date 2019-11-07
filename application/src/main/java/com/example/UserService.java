package com.example;

import com.example.exceptions.EmailAreadyTakenException;
import com.example.exceptions.UserNotFoundException;
import com.example.exceptions.UsernameAlreadyTakenException;
import com.example.ports.in.AuthPort;
import com.example.ports.in.GetProfileQuery;
import com.example.ports.in.GetUserPort;
import com.example.ports.out.FollowUserPort;
import com.example.ports.out.LoadProfilePort;
import com.example.ports.out.RegisterUserPort;
import com.example.ports.out.UpdateUserPort;
import com.example.usecases.FollowUserUseCase;
import java.util.Optional;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Transactional
public class UserService implements FollowUserUseCase, GetProfileQuery {

  private LoadProfilePort profilePort;

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

  private final GetUserPort getUserPort;
  private final RegisterUserPort registerUserPort;
  private final AuthPort authService;
  private final UpdateUserPort updateUserPort;
  private final FollowUserPort followUserPort;

  @Override
  public Profile follow(User followed, User follower) {
    return this.followUserPort.saveFollowRelation(followed, follower);
  }

  @Override
  public Profile unfollow(User followed, User follower) {
    return this.followUserPort.removeFollowRelation(followed, follower);
  }

  public User register(String username, String email, String password) {
    assert username != null;
    assert email != null;
    assert password != null;
    assert !username.isBlank();
    assert !email.isBlank();
    assert !password.isBlank();
    assert password.length() > 5;
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

    return this.updateUserPort.save(previousUsername, existing);
  }

  private Boolean isValidEmail(String email) {
    return true; // TODO implement later
  }
}
