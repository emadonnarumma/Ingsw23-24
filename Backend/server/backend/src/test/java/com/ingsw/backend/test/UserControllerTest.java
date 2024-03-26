package com.ingsw.backend.test;

import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.UserRepository;
import com.ingsw.backend.service.UserService;
import com.ingsw.backend.service.dbservice.UserDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserDbService(userRepository);
    }

    @Test
    void testFindUserByEmailAndRole() {
        String email = "test@example.com";
        Role role = Role.BUYER;

        Buyer expectedUser = new Buyer();
        expectedUser.setEmail(email);
        expectedUser.setRole(role);


        when(userRepository.findByEmailAndRole(email, role)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.getUser(email, role);

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
    }
}