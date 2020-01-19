package com.example.adapters.web.dto.output;

import com.example.application.domain.model.Profile;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileResponse implements Serializable {

  private ProfileResponseBody profile;

  public static GetProfileResponse mapProfileToProfileResponse(Profile profile) {
    return GetProfileResponse.builder()
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
