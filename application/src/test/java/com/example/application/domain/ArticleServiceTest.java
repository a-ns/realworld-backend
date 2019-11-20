package com.example.application.domain;

import com.example.application.domain.ports.in.UpdateArticlePort;
import com.example.application.domain.ports.out.FindArticlePort;
import com.example.application.domain.ports.in.CreateArticlePort;
import com.example.application.domain.ports.in.DeleteArticlePort;
import com.example.application.domain.ports.in.FavoritePort;
import com.example.application.domain.ports.out.ArticleFavoriteCountPort;
import com.example.application.domain.ports.out.GetProfileQuery;
import com.example.application.domain.ports.out.IsFavoritedPort;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    ArticleService sut;
    @Mock
    private CreateArticlePort createArticlePort;
    @Mock
    private FindArticlePort findArticlePort;
    @Mock
    private UpdateArticlePort updateArticlePort;
    @Mock
    private DeleteArticlePort deleteArticlePort;
    @Mock private GetProfileQuery getProfileQuery;
    @Mock private IsFavoritedPort isFavoritedPort;
    @Mock private ArticleFavoriteCountPort articleFavoriteCountPort;
    @Mock private FavoritePort favoritePort;
}
