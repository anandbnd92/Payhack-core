package com.gamification.gamification.Controller;

import com.gamification.gamification.Entity.User;
import com.gamification.gamification.Repository.UserRepository;
import com.gamification.gamification.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        User user = new User();
        String userPassword = "testPassword";
        user.setUsername("testuser");
        when(passwordEncoder.encode(userPassword)).thenReturn("encryptedPassword");
        user.setPassword(passwordEncoder.encode(userPassword));
        ResponseEntity<?> response = authController.register(user);

        // Then
        verify(userRepository).save(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void testLogin_Success() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(user.getUsername()))
                .thenReturn("dummyToken");

        // When
        ResponseEntity<?> response = authController.login(user);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User authenticated successfully", ((LoginResponse) response.getBody()).getMessage());
//        assertEquals("dummyToken", ((LoginResponse) response.getBody()).getJwt());
    }

    @Test
    void testLogin_Failure() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(authenticationManager.authenticate(any()))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // When
        ResponseEntity<?> response = authController.login(user);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode().value());
        assertEquals("Invalid username or password", response.getBody());
    }
}
