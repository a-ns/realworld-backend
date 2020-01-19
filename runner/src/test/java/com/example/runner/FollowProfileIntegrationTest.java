package com.example.runner;

import com.example.adapters.web.dto.ProfileResponse;
import com.example.adapters.web.dto.UserRegistration;
import com.example.adapters.web.dto.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FollowProfileIntegrationTest {
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  @DisplayName("User can follow another user")
  void user_can_follow_another_user() {
    // Arrange
    String email = "hello-3@world.com";
    String password = "password";
    String username = "alex1";
    HttpEntity<UserRegistration> user1 =
        new HttpEntity<>(
            UserRegistration.builder()
                .user(
                    UserRegistration.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    HttpEntity<UserRegistration> user2 =
        new HttpEntity<>(
            UserRegistration.builder()
                .user(
                    UserRegistration.Body.builder()
                        .email("hello-4@world.com")
                        .password(password)
                        .username("alex2")
                        .build())
                .build());

    // Act
    ResponseEntity<UserResponse> user1Res =
        restTemplate.postForEntity(createURLWithPort("/users"), user1, UserResponse.class);
    ResponseEntity<UserResponse> user2Res =
        restTemplate.postForEntity(createURLWithPort("/users"), user2, UserResponse.class);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Token " + user2Res.getBody().getUser().getToken());
    HttpEntity<?> followHeaders = new HttpEntity<>(null, headers);

    ResponseEntity<ProfileResponse> profileResponse =
        restTemplate.postForEntity(
            createURLWithPort("/profiles/alex1/follow"), followHeaders, ProfileResponse.class);
    Assertions.assertTrue(profileResponse.getBody().getProfile().getFollowing());

    ResponseEntity<ProfileResponse> unfollowProfileResponse =
        restTemplate.postForEntity(
            createURLWithPort("/profiles/alex1/unfollow"), followHeaders, ProfileResponse.class);
    Assertions.assertFalse(unfollowProfileResponse.getBody().getProfile().getFollowing());

    ResponseEntity<ProfileResponse> unfollowedProfileResponse =
        restTemplate.exchange(
            createURLWithPort("/profiles/alex1"),
            HttpMethod.GET,
            followHeaders,
            ProfileResponse.class);
    Assertions.assertFalse(unfollowedProfileResponse.getBody().getProfile().getFollowing());
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
