package com.example.adapters.event;

public interface Listener<T extends Event> {
  String listensOn();

  void process(T event);

  Class<T> type();
}
