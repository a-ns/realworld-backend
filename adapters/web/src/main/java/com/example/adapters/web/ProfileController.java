package com.example.adapters.web;

import com.example.adapters.web.dto.output.GetProfileResponse;
import com.example.application.domain.exceptions.UserNotFoundException;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.FollowUserUseCase;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.GetUserPort;
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
  public ResponseEntity<GetProfileResponse> follow(
      @AuthenticationPrincipal User requestingToFollow, @PathVariable String usernameToFollow) {
    try {
      User userToFollow =
          getUserPort.getUserByUsername(usernameToFollow).orElseThrow(UserNotFoundException::new);
      return ResponseEntity.ok(
          GetProfileResponse.mapProfileToProfileResponse(
              followUserUseCase.follow(userToFollow, requestingToFollow)));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{usernameToUnfollow}/unfollow")
  public ResponseEntity<GetProfileResponse> unfollow(
      @AuthenticationPrincipal User requestingToUnfollow, @PathVariable String usernameToUnfollow) {
    try {
      User userToUnfollow =
          getUserPort.getUserByUsername(usernameToUnfollow).orElseThrow(UserNotFoundException::new);
      return ResponseEntity.ok(
          GetProfileResponse.mapProfileToProfileResponse(
              followUserUseCase.unfollow(userToUnfollow, requestingToUnfollow)));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{username}")
  public ResponseEntity<GetProfileResponse> get(
      @AuthenticationPrincipal User authorized, @PathVariable String username) {
    try {
      return ResponseEntity.ok(
          GetProfileResponse.mapProfileToProfileResponse(
              getProfileQuery.getProfile(
                  username, authorized == null ? Optional.empty() : Optional.of(authorized))));
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
