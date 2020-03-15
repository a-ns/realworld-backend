package com.example.integration.tests;

import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.input.UserUpdatePayload;
import com.example.adapters.web.dto.output.GetUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class UserUpdateIntegrationTest extends IntegrationTest {

  @DisplayName("User is updated with specified fields")
  @Test
  void user_is_updated() {

    // Arrange
    String email = "test-12@world.com";
    String password = "password";
    String username = "test12";
    HttpEntity<UserRegistrationPayload> body =
        new HttpEntity<>(
            UserRegistrationPayload.builder()
                .user(
                    UserRegistrationPayload.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    ResponseEntity<GetUserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, GetUserResponse.class);

    var headers = new HttpHeaders();
    headers.add("Authorization", "Token " + response.getBody().getUser().getToken());

    var requestBody =
        new HttpEntity<>(
            UserUpdatePayload.builder()
                .user(
                    UserUpdatePayload.User.builder()
                        .bio("new bio")
                        .email("new-email@email.com")
                        .image("new image")
                        .password("something else")
                        .username("new-name")
                        .build())
                .build(),
            headers);
    // Act
    ResponseEntity<GetUserResponse> replaced =
        restTemplate.exchange(
            createURLWithPort("/user"), HttpMethod.PUT, requestBody, GetUserResponse.class);
    // Assert

    Assertions.assertEquals("new bio", replaced.getBody().getUser().getBio());
    Assertions.assertEquals("new-email@email.com", replaced.getBody().getUser().getEmail());
    Assertions.assertEquals("new image", replaced.getBody().getUser().getImage());
    Assertions.assertNotEquals(
        response.getBody().getUser().getToken(), replaced.getBody().getUser().getToken());
    Assertions.assertEquals("new-name", replaced.getBody().getUser().getUsername());
  }
}
