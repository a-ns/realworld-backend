package com.example.application.domain.services;

import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.FollowUserPort;
import com.example.application.domain.ports.in.LoadProfilePort;
import com.example.application.domain.ports.in.RegisterUserPort;
import com.example.application.domain.ports.in.UpdateUserPort;
import com.example.application.domain.ports.out.AuthPort;
import com.example.application.domain.ports.out.GetUserPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("User use cases")
public class UserServiceTest {
    @InjectMocks
    private UserService sut;
    @Mock
    private LoadProfilePort profilePort;
    @Mock private  GetUserPort getUserPort;
    @Mock private  RegisterUserPort registerUserPort;
    @Mock private  AuthPort authService;
    @Mock private  UpdateUserPort updateUserPort;
    @Mock private  FollowUserPort followUserPort;

    @Nested
    @DisplayName("Update user")
    class UpdateUser {
        @Test
        @DisplayName("Update by email")
        public void update_by_email(){
            // Arrange

            User updatePayload = User.builder().email("new@email.com").build();
            Optional<User> foundUser = Optional.of(User.builder().username("user").email("old@email.com").build());
            Mockito.when(getUserPort.getUserByUsername("user")).thenReturn(foundUser);
            Mockito.when(getUserPort.getUserByEmail("new@email.com")).thenReturn(Optional.empty());
            //Mockito.when(updateUserPort.save( ))
            // Act
            User actual = sut.updateUser("user", updatePayload);
            // Assert
            Assertions.assertEquals(actual.getEmail(), "new@email.com");
        }
    }

}
