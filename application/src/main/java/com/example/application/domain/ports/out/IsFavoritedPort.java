package com.example.application.domain.ports.out;

public interface IsFavoritedPort {

  Boolean isArticleFavoritedBy(Integer article, Integer userId);
}
