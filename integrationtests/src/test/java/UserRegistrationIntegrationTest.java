import com.example.adapters.web.dto.UserRegistration;
import com.example.runner.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Disabled
    void user_can_register(){
        // Arrange
        String email = "hello@world.com";
        String password = "password";
        String username = "alex";
        UserRegistration body = UserRegistration.builder().user(UserRegistration.Body.builder().email(email).password(password).username(username).build()).build();
        // Act
        String response = restTemplate.postForObject(
                createURLWithPort("/users"), body, String.class);
        // Assert
     //   Assertions.assertEquals(response, "hello world");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
