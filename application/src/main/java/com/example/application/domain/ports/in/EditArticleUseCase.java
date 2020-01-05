package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.EditArticleCommand;

public interface EditArticleUseCase {

  Article editArticle(EditArticleCommand draftArticle) throws ArticleNotFoundException;
}
