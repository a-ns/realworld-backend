package com.example.usecases;

import com.example.Article;
import com.example.PublishArticleCommand;
import com.example.exceptions.ArticleAlreadyExistsException;

public interface PublishArticleUseCase {

  Article publishArticle(PublishArticleCommand article) throws ArticleAlreadyExistsException;
}
