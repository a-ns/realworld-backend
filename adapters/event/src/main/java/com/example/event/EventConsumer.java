package com.example.event;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EventConsumer {

  List<Listener<PostEvent>> listeners;
  Gson gson;
  Logger logger;

  @RabbitListener(queues = "exampleApp")
  public void receive(String message) {
    logger.warn("Received Message: " + message);
    Map<String, Object> json;
    try {
      json = gson.fromJson(message, Map.class);
    } catch (Exception e) {
      logger.warn(e.getStackTrace().toString());
      return; // is not an object
    }
    String eventType = (String) json.get("eventType");
    if (eventType == null) {
      return; // throw an exception instead or something
    }

    this.listeners.stream()
        .filter(listener -> listener.listensOn().equals(eventType))
        .forEach(
            listener ->
                listener.process(
                    gson.fromJson(gson.toJson(json.get("data")), (Type) listener.type())));
  }
}
