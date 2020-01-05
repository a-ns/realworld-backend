package com.example.application.domain.ports.in;

public interface DeleteArticleUseCase {

  Boolean deleteArticleBySlug(String slug);
}
