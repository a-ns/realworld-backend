package com.example.runner;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.example")
public class ArchitectureTest {
  @ArchTest
  static final ArchRule onion_architecture_is_respected =
      onionArchitecture()
          .domainModels("..domain.model..")
          .domainServices("..domain.services..")
          .applicationServices("..application..")
          .adapter("web", "..adapters.web..")
          .adapter("persistence", "..adapters.persistence..")
          .adapter("event", "..adapters.event..");
}
