package com.example.web;

import com.example.User;
import com.example.UserService;
import com.example.web.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private UserService userService;
  private UserWebMapper userMapper;
  private AuthService auth;

  @PutMapping
  @Auth
  public ResponseEntity<UserResponse> replace(
      @RequestBody UserUpdateRequest body,
      @AuthenticationPrincipal User user,
      @RequestHeader("Authorization") String token) {
    String currentUser = user.getUsername();
    try {
      User mapped = this.userService.updateUser(currentUser, userMapper.mapResponseToUser(body));
      mapped.setToken(auth.generateToken(mapped));
      return ResponseEntity.ok(userMapper.mapUserToResponse(mapped));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
