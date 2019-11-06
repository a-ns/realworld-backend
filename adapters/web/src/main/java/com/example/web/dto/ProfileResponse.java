package com.example.web.dto;

import com.example.Profile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {

  private ProfileResponseBody profile;

  public static ProfileResponse mapProfileToProfileResponse(Profile profile) {
    return ProfileResponse.builder()
        .profile(
            ProfileResponseBody.builder()
                .bio(profile.getBio())
                .following(profile.getFollowing())
                .image(profile.getImage())
                .username(profile.getUsername())
                .build())
        .build();
  }

  @Data
  @Builder
  public static class ProfileResponseBody {
    private String username;
    private String bio;
    private String image;
    private Boolean following;
  }
}
