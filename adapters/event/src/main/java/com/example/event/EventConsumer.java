package com.example.event;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventConsumer {

  List<Listener> listeners;
  JsonParser jsonParser;
  Gson gson;

  @RabbitListener(queues = "${queue.name}")
  public void receive(String message) {
    Map<String, Object> json;
    try {
      json = jsonParser.parseMap(message);
    } catch (Exception e) {
      return; // is not an object
    }
    String eventType = (String) json.get("eventType");
    if (eventType == null) {
      return; // throw an exception instead or something
    }
    this.listeners.stream()
        .filter(listener -> listener.listensOn().equals(eventType))
        .forEach(listener -> listener.process(gson.fromJson(message, (Type) listener.type())));
  }
}
