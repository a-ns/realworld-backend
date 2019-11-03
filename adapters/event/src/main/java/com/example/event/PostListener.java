package com.example.event;

import com.example.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PostListener implements Listener<PostEvent> {

  private final PostService service;

  private final PostEventMapper mapper;

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
