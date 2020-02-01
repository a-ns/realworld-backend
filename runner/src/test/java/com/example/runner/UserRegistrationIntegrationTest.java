package com.example.runner;

import com.example.adapters.web.dto.input.UserLoginPayload;
import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.output.GetProfileResponse;
import com.example.adapters.web.dto.output.GetUserResponse;
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
    classes = SpringRunner.class,
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
    // Act
    ResponseEntity<GetUserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, GetUserResponse.class);
    // Assert
    Assertions.assertEquals(username, response.getBody().getUser().getUsername());

    // Query for registered user
    // Act
    ResponseEntity<GetProfileResponse> response2 =
        restTemplate.getForEntity(
            createURLWithPort("/profiles/" + username), GetProfileResponse.class);

    Assertions.assertEquals(username, response.getBody().getUser().getUsername());
  }

  @Test
  void cannot_login_with_wrong_password() {
    // Arrange
    String email = "hello-1@world.com";
    String password = "password";
    String username = "wrong";
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
    HttpEntity<UserLoginPayload> loginBody =
        new HttpEntity<UserLoginPayload>(
            UserLoginPayload.builder()
                .user(
                    UserLoginPayload.Body.builder()
                        .email(email)
                        .password(password + "wrong")
                        .build())
                .build());

    // Act
    ResponseEntity<GetUserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, GetUserResponse.class);
    ResponseEntity<GetUserResponse> loginResponse =
        restTemplate.postForEntity(
            createURLWithPort("/users/login"), loginBody, GetUserResponse.class);
    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, loginResponse.getStatusCode());
  }

  @Test
  void login() {
    // Arrange
    String email = "hello@world.com";
    String password = "password";
    String username = "alex";
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
    HttpEntity<UserLoginPayload> loginBody =
        new HttpEntity<UserLoginPayload>(
            UserLoginPayload.builder()
                .user(UserLoginPayload.Body.builder().email(email).password(password).build())
                .build());

    // Act
    ResponseEntity<GetUserResponse> response =
        restTemplate.postForEntity(createURLWithPort("/users"), body, GetUserResponse.class);
    ResponseEntity<GetUserResponse> loginResponse =
        restTemplate.postForEntity(
            createURLWithPort("/users/login"), loginBody, GetUserResponse.class);
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
