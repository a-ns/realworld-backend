package com.example.event;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostDeleteListener implements Listener<PostEvent> {

  private Logger logger;

  @Override
  public String listensOn() {
    return "PostCreateCommand";
  }

  @Override
  public void process(PostEvent event) {
    logger.warn("In PostDeleteListener");
    return;
  }

  @Override
  public Class<PostEvent> type() {
    return PostEvent.class;
  }
}
