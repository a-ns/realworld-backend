package com.example.application.domain.services.article;

import static org.junit.jupiter.api.Assertions.*;

import com.example.application.domain.ports.out.LoadFavoritedPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetFavoriteQueryService")
class GetFavoriteQueryServiceTest {

  @InjectMocks GetFavoriteQueryService sut;
  @Mock LoadFavoritedPort loadFavoritedPort;

  @DisplayName("returns true if the user has favorited the article")
  @Test
  void returns_true_when_favorited() {
    // Arrange
    Mockito.when(loadFavoritedPort.isArticleFavoritedBy(5678, 1234)).thenReturn(true);
    // Act
    Boolean actual = sut.hasFavorited(1234, 5678);
    // Assert
    Assertions.assertEquals(true, actual);
  }

  @DisplayName("returns false if the user has not favorited the article")
  @Test
  void returns_false_when_not_favorited() {
    // Arrange
    Mockito.when(loadFavoritedPort.isArticleFavoritedBy(5678, 1234)).thenReturn(false);
    // Act
    Boolean actual = sut.hasFavorited(1234, 5678);
    // Assert
    Assertions.assertEquals(false, actual);
  }
}
