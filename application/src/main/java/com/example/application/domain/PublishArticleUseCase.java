package com.example.application.domain;

import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.PublishArticleCommand;

public interface PublishArticleUseCase {

  Article publishArticle(PublishArticleCommand article) throws ArticleAlreadyExistsException;
}
