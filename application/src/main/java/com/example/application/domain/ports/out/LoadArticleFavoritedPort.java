package com.example.application.domain.ports.out;

public interface LoadArticleFavoritedPort {

  Boolean isArticleFavoritedBy(Integer article, Integer userId);
}
