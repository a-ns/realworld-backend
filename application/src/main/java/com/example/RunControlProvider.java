package com.example;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class RunControlProvider {

  private LocalDate runControl;
}
