package com.example.runner;

import com.example.adapters.web.dto.ProfileResponse;
import com.example.adapters.web.dto.UserLogin;
import com.example.adapters.web.dto.UserRegistration;
import com.example.adapters.web.dto.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationIntegrationTest {
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void user_can_register_and_query() {
    // Registration
    // Arrange
    String email = "test@world.com";
    String password = "password";
    String username = "test";
    HttpEntity<UserRegistration> body =
        new HttpEntity<>(
            UserRegistration.builder()
                .user(
                    UserRegistration.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    // Act
    ResponseEntity<UserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, UserResponse.class);
    // Assert
    Assertions.assertEquals(username, response.getBody().getUser().getUsername());

    // Query for registered user
    // Act
    ResponseEntity<ProfileResponse> response2 =
        restTemplate.getForEntity(
            createURLWithPort("/profiles/" + username), ProfileResponse.class);

    Assertions.assertEquals(username, response.getBody().getUser().getUsername());
  }

  @Test
  void cannot_login_with_wrong_password() {
    // Arrange
    String email = "hello-1@world.com";
    String password = "password";
    String username = "wrong";
    HttpEntity<UserRegistration> body =
        new HttpEntity<>(
            UserRegistration.builder()
                .user(
                    UserRegistration.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    HttpEntity<UserLogin> loginBody =
        new HttpEntity<UserLogin>(
            UserLogin.builder()
                .user(UserLogin.Body.builder().email(email).password(password + "wrong").build())
                .build());

    // Act
    ResponseEntity<UserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, UserResponse.class);
    ResponseEntity<UserResponse> loginResponse =
        restTemplate.postForEntity(
            createURLWithPort("/users/login"), loginBody, UserResponse.class);
    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
  }

  @Test
  void login() {
    // Arrange
    String email = "hello@world.com";
    String password = "password";
    String username = "alex";
    HttpEntity<UserRegistration> body =
        new HttpEntity<>(
            UserRegistration.builder()
                .user(
                    UserRegistration.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    HttpEntity<UserLogin> loginBody =
        new HttpEntity<UserLogin>(
            UserLogin.builder()
                .user(UserLogin.Body.builder().email(email).password(password).build())
                .build());

    // Act
    ResponseEntity<UserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, UserResponse.class);
    ResponseEntity<UserResponse> loginResponse =
        restTemplate.postForEntity(
            createURLWithPort("/users/login"), loginBody, UserResponse.class);
    // Assert
    Assertions.assertEquals(
        loginResponse.getBody().getUser().getEmail(), response.getBody().getUser().getEmail());
    Assertions.assertEquals(
        loginResponse.getBody().getUser().getUsername(),
        response.getBody().getUser().getUsername());
    Assertions.assertEquals(
        loginResponse.getBody().getUser().getBio(), response.getBody().getUser().getBio());
    Assertions.assertEquals(
        loginResponse.getBody().getUser().getImage(), response.getBody().getUser().getImage());
    Assertions.assertNotNull(loginResponse.getBody().getUser().getToken());
    Assertions.assertNotNull(response.getBody().getUser().getToken());
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
