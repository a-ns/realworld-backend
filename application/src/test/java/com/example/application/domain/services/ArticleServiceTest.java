package com.example.application.domain.services;

import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.DeleteArticlePort;
import com.example.application.domain.ports.out.LoadArticleFavoriteCountPort;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.LoadFavoritedPort;
import com.example.application.domain.ports.out.SaveArticlePort;
import com.example.application.domain.ports.out.SaveFavoritePort;
import com.example.application.domain.ports.out.UpdateArticlePort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

  @InjectMocks ArticleService sut;
  @Mock private SaveArticlePort saveArticlePort;
  @Mock private LoadArticlePort loadArticlePort;
  @Mock private UpdateArticlePort updateArticlePort;
  @Mock private DeleteArticlePort deleteArticlePort;
  @Mock private GetProfileQuery getProfileQuery;
  @Mock private LoadFavoritedPort loadFavoritedPort;
  @Mock private LoadArticleFavoriteCountPort loadArticleFavoriteCountPort;
  @Mock private SaveFavoritePort saveFavoritePort;
}
