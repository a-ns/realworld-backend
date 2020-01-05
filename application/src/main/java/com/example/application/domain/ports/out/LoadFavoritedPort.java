package com.example.application.domain.ports.out;

public interface LoadFavoritedPort {

  Boolean isArticleFavoritedBy(Integer article, Integer userId);
}
