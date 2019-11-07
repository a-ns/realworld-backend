package com.example.usecases;

import com.example.Article;
import com.example.EditArticleCommand;
import com.example.exceptions.ArticleNotFoundException;

public interface EditArticleUseCase {

  Article editArticle(EditArticleCommand draftArticle) throws ArticleNotFoundException;
}
