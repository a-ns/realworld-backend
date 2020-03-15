package com.example.integration.tests;

import com.example.runner.SpringRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SpringRunner.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
  @LocalServerPort public int port;

  @Autowired public TestRestTemplate restTemplate;

  public String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
