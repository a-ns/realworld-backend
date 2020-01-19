package com.example.runner;

import com.example.adapters.web.dto.input.CreateArticlePayload;
import com.example.adapters.web.dto.input.UserRegistrationPayload;
import com.example.adapters.web.dto.output.GetArticleResponse;
import com.example.adapters.web.dto.output.GetUserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateAndFindArticleIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void create_and_find_article() {

    // Arrange
    String email = "hello-7@world.com";
    String password = "password";
    String username = "alex7";
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
                        .title("article title1")
                        .build())
                .build(),
            headers);

    restTemplate.postForEntity(
        createURLWithPort("/articles/"), articlePayload, GetArticleResponse.class);
    var articleFound =
        restTemplate.getForEntity(
            createURLWithPort("/articles/article-title1"), GetArticleResponse.class);

    Assertions.assertEquals("article body", articleFound.getBody().getBody());
    Assertions.assertEquals("article description", articleFound.getBody().getDescription());
    Assertions.assertEquals("article title1", articleFound.getBody().getTitle());
    Assertions.assertEquals(0, articleFound.getBody().getFavoritesCount());
    Assertions.assertEquals(false, articleFound.getBody().getFavorited());
    Assertions.assertEquals("article-title1", articleFound.getBody().getSlug());

    Assertions.assertEquals("alex7", articleFound.getBody().getAuthor().getUsername());
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
