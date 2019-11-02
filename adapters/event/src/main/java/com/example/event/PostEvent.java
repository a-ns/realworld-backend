package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PostEvent implements Event {

  private String author;
  private String title;
  private String text;
  private String category;

  @Override
  public String getEventType() {
    return "PostCreateCommand";
  }
}
