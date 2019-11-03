package com.example.runner;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
  @Bean
  public Exchange eventExchange() {
    return new TopicExchange("eventExchange");
  }

  @Bean
  public Queue queue() {
    return new Queue("exampleApp");
  }

  @Bean
  public Binding binding(Queue queue, Exchange eventExchange) {
    return BindingBuilder.bind(queue).to(eventExchange).with("*").noargs();
  }
}
