package com.example.adapters.web.dto.output;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.Profile;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleResponse implements Serializable {
  private String slug;
  private String title;
  private String description;
  private String body;
  private List<String> tagList;
  private String createdAt;
  private String updatedAt;
  private Boolean favorited;
  private Integer favoritesCount;
  private GetProfileResponse.ProfileResponseBody author;

  public static GetArticleResponse mapArticleToArticleResponse(Article article, Profile profile) {

    return GetArticleResponse.builder()
        .body(article.getBody())
        .createdAt(article.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
        .description(article.getDescription())
        .favorited(article.getFavorited())
        .favoritesCount(article.getFavoritesCount())
        .slug(article.getSlug())
        .tagList(article.getTags())
        .title(article.getTitle())
        .updatedAt(article.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
        .author(
            GetProfileResponse.ProfileResponseBody.builder()
                .bio(profile.getBio())
                .following(profile.getFollowing())
                .image(profile.getImage())
                .username(profile.getUsername())
                .build())
        .build();
  }
}
