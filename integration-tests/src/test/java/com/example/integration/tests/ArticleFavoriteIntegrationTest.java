package com.example.integration.tests;

import com.example.adapters.web.dto.input.CreateArticlePayload;
import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.output.GetArticleResponse;
import com.example.adapters.web.dto.output.GetUserResponse;
import com.example.runner.SpringRunner;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SpringRunner.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleFavoriteIntegrationTest extends IntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @DisplayName("A User can favorite an article")
  @Test
  void user_can_favorite_article() {

    // Arrange
    String email = "hello-5@world.com";
    String password = "password";
    String username = "alex5";
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

    // Act
    var user1Res =
        restTemplate.postForEntity(createURLWithPort("/users"), user1, GetUserResponse.class);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Token " + user1Res.getBody().getUser().getToken());
    HttpEntity<CreateArticlePayload> articlePayload =
        new HttpEntity<>(
            CreateArticlePayload.builder()
                .article(
                    CreateArticlePayload.Body.builder()
                        .body("article body")
                        .description("article description")
                        .title("article title")
                        .build())
                .build(),
            headers);
    var favoritePayload = new HttpEntity<>(null, headers);

    var articleRes =
        restTemplate.postForEntity(
            createURLWithPort("/articles/"), articlePayload, GetArticleResponse.class);
    var favoritedArticle =
        restTemplate.postForEntity(
            createURLWithPort("/articles/" + articleRes.getBody().getSlug() + "/favorite"),
            favoritePayload,
            GetArticleResponse.class);

    Assertions.assertTrue(favoritedArticle.getBody().getFavorited());
    Assertions.assertEquals(1, favoritedArticle.getBody().getFavoritesCount());

    var unfavoritedArticle =
        restTemplate.exchange(
            createURLWithPort("/articles/" + articleRes.getBody().getSlug() + "/favorite"),
            HttpMethod.DELETE,
            favoritePayload,
            GetArticleResponse.class);
    Assertions.assertFalse(unfavoritedArticle.getBody().getFavorited());
    Assertions.assertEquals(0, unfavoritedArticle.getBody().getFavoritesCount());
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
