package com.example.application.domain.services;

import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import com.example.application.domain.ports.out.LoadProfilePort;
import com.example.application.domain.ports.out.SaveUserPort;
import com.example.application.domain.ports.out.UpdateUserPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("User use cases")
public class UserServiceTest {
  @InjectMocks private UserService sut;
  @Mock private LoadProfilePort profilePort;
  @Mock private GetUserPort getUserPort;
  @Mock private SaveUserPort saveUserPort;
  @Mock private AuthPort authService;
  @Mock private UpdateUserPort updateUserPort;

  @Nested
  @DisplayName("Update user")
  class UpdateUser {
    //        @Test
    //        @DisplayName("Update by email")
    //        @Disabled
    //        public void update_by_email(){
    //            // Arrange
    //
    //            User updatePayload = User.builder().email("new@email.com").build();
    //            Optional<User> foundUser =
    // Optional.of(User.builder().username("user").email("old@email.com").build());
    //            Mockito.when(getUserPort.getUserByUsername("user")).thenReturn(foundUser);
    //
    // Mockito.when(getUserPort.getUserByEmail("new@email.com")).thenReturn(Optional.empty());
    //            //Mockito.when(updateUserPort.save( ))
    //            // Act
    //            User actual = sut.updateUser("user", updatePayload);
    //            // Assert
    //            Assertions.assertEquals(actual.getEmail(), "new@email.com");
    //        }
  }
}