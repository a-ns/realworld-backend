package com.example.event;

import com.example.PostService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostListener implements Listener<PostEvent> {

  private final PostService service;

  private final PostMapper mapper;

  @Override
  public String listensOn() {
    return "PostCreateCommand";
  }

  @Override
  public void process(PostEvent event) {
    service.createPost(mapper.eventDataToDomain(event));
  }

  @Override
  public Class<PostEvent> type() {
    return PostEvent.class;
  }
}
