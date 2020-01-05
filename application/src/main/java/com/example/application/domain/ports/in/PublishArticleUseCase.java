package com.example.application.domain.ports.in;

import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.PublishArticleCommand;

public interface PublishArticleUseCase {

  Article publishArticle(PublishArticleCommand article) throws ArticleAlreadyExistsException;
}
