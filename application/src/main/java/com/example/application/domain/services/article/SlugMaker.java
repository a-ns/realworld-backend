package com.example.application.domain.services.article;

import org.springframework.stereotype.Component;

@Component
class SlugMaker {

  public String createSlug(String title) {
    return title.replaceAll("\\s+", "-");
  }
}
