package com.example.application.domain.usecases;

import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.EditArticleCommand;
import com.example.application.domain.model.Article;

public interface EditArticleUseCase {

  Article editArticle(EditArticleCommand draftArticle) throws ArticleNotFoundException;
}
