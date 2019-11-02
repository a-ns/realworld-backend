package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

  @Autowired private RunControlProvider rcp;

  public void run() {
    System.out.println("The date is " + rcp.getRunControl().toString());
  }
}
