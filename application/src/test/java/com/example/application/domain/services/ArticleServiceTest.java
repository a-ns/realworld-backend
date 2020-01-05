package com.example.application.domain.services;

import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.in.UpdateArticlePort;
import com.example.application.domain.ports.out.ArticleFavoriteCountPort;
import com.example.application.domain.ports.out.DeleteArticlePort;
import com.example.application.domain.ports.out.FindArticlePort;
import com.example.application.domain.ports.out.IsFavoritedPort;
import com.example.application.domain.ports.out.SaveArticlePort;
import com.example.application.domain.ports.out.SaveFavoritePort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

  @InjectMocks ArticleService sut;
  @Mock private SaveArticlePort saveArticlePort;
  @Mock private FindArticlePort findArticlePort;
  @Mock private UpdateArticlePort updateArticlePort;
  @Mock private DeleteArticlePort deleteArticlePort;
  @Mock private GetProfileQuery getProfileQuery;
  @Mock private IsFavoritedPort isFavoritedPort;
  @Mock private ArticleFavoriteCountPort articleFavoriteCountPort;
  @Mock private SaveFavoritePort saveFavoritePort;
}
