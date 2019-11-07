package com.example.web;

import com.example.User;
import com.example.exceptions.UserNotFoundException;
import com.example.ports.out.GetProfileQuery;
import com.example.ports.out.GetUserPort;
import com.example.usecases.FollowUserUseCase;
import com.example.web.dto.ProfileResponse;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {

  private GetUserPort getUserPort;
  private FollowUserUseCase followUserUseCase;
  private GetProfileQuery getProfileQuery;

  @PostMapping("/{usernameToFollow}/follow")
  public ResponseEntity<ProfileResponse> follow(
      @AuthenticationPrincipal User requestingToFollow, @PathVariable String usernameToFollow) {
    try {
      User userToFollow =
          getUserPort.getUserByUsername(usernameToFollow).orElseThrow(UserNotFoundException::new);
      return ResponseEntity.ok(
          ProfileResponse.mapProfileToProfileResponse(
              followUserUseCase.follow(userToFollow, requestingToFollow)));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{usernameToUnfollow}/unfollow")
  public ResponseEntity<ProfileResponse> unfollow(
      @AuthenticationPrincipal User requestingToUnfollow, @PathVariable String usernameToUnfollow) {
    try {
      User userToUnfollow =
          getUserPort.getUserByUsername(usernameToUnfollow).orElseThrow(UserNotFoundException::new);
      return ResponseEntity.ok(
          ProfileResponse.mapProfileToProfileResponse(
              followUserUseCase.unfollow(userToUnfollow, requestingToUnfollow)));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{username}")
  public ResponseEntity<ProfileResponse> get(
      @AuthenticationPrincipal User authorized, @PathVariable String username) {
    try {
      return ResponseEntity.ok(
          ProfileResponse.mapProfileToProfileResponse(
              getProfileQuery.getProfile(
                  username, authorized == null ? Optional.empty() : Optional.of(authorized))));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
