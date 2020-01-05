package com.example.adapters.web;

import com.example.adapters.web.dto.UserResponse;
import com.example.adapters.web.dto.UserUpdateRequest;
import com.example.adapters.web.dto.UserWebMapper;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.UpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private UserWebMapper userMapper;
  private AuthService auth;
  private UpdateUserUseCase userUpdator;

  @PutMapping
  public ResponseEntity<UserResponse> replace(
      @RequestBody UserUpdateRequest body, @AuthenticationPrincipal User user) {
    String currentUser = user.getUsername();
    try {
      User mapped = this.userUpdator.updateUser(currentUser, userMapper.mapResponseToUser(body));
      mapped.setToken(auth.generateToken(mapped));
      return ResponseEntity.ok(userMapper.mapUserToResponse(mapped));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
