package com.example.integration.tests;

import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.output.GetProfileResponse;
import com.example.adapters.web.dto.output.GetUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class FollowProfileIntegrationTest extends IntegrationTest {

  @Test
  @DisplayName("User can follow another user")
  void user_can_follow_another_user() {
    // Arrange
    String email = "hello-3@world.com";
    String password = "password";
    String username = "alex1";
    HttpEntity<UserRegistrationPayload> user1 =
        new HttpEntity<>(
            UserRegistrationPayload.builder()
                .user(
                    UserRegistrationPayload.Body.builder()
                        .email(email)
                        .password(password)
                        .username(username)
                        .build())
                .build());
    HttpEntity<UserRegistrationPayload> user2 =
        new HttpEntity<>(
            UserRegistrationPayload.builder()
                .user(
                    UserRegistrationPayload.Body.builder()
                        .email("hello-4@world.com")
                        .password(password)
                        .username("alex2")
                        .build())
                .build());

    // Act
    ResponseEntity<GetUserResponse> user1Res =
        this.restTemplate.postForEntity(createURLWithPort("/users"), user1, GetUserResponse.class);
    ResponseEntity<GetUserResponse> user2Res =
        restTemplate.postForEntity(createURLWithPort("/users"), user2, GetUserResponse.class);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Token " + user2Res.getBody().getUser().getToken());
    HttpEntity<?> followHeaders = new HttpEntity<>(null, headers);

    ResponseEntity<GetProfileResponse> profileResponse =
        restTemplate.postForEntity(
            createURLWithPort("/profiles/alex1/follow"), followHeaders, GetProfileResponse.class);
    Assertions.assertTrue(profileResponse.getBody().getProfile().getFollowing());

    ResponseEntity<GetProfileResponse> unfollowProfileResponse =
        restTemplate.postForEntity(
            createURLWithPort("/profiles/alex1/unfollow"), followHeaders, GetProfileResponse.class);
    Assertions.assertFalse(unfollowProfileResponse.getBody().getProfile().getFollowing());

    ResponseEntity<GetProfileResponse> unfollowedProfileResponse =
        restTemplate.exchange(
            createURLWithPort("/profiles/alex1"),
            HttpMethod.GET,
            followHeaders,
            GetProfileResponse.class);
    Assertions.assertFalse(unfollowedProfileResponse.getBody().getProfile().getFollowing());
  }
}
