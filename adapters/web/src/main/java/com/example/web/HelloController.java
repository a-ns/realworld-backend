package com.example.web;

import com.example.RunControlProvider;
import com.example.SomeService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
  private final RunControlProvider rcp;

  private final SomeService someService;

  @RequestMapping("/")
  public String index() {
    this.rcp.setRunControl(LocalDate.now());
    this.someService.run();
    return "Greetings from Spring Boot!";
  }
}
