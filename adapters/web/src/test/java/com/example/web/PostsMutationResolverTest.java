package com.example.web;

import com.example.ports.out.AddPostPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Posts Mutations")
public class PostsMutationResolverTest {
  @Mock AddPostPort postDao;

  @InjectMocks PostsMutationResolver sut;

  @Test
  @DisplayName("Adds a post")
  public void test() {
    // Arrange

    // Act
    Boolean actual = sut.addPost();
    // Assert
    Assertions.assertEquals(true, actual);
  }
}
