package com.kuddle.auth.service;

import com.kuddle.auth.dto.LoginRequest;
import com.kuddle.auth.dto.RegisterRequest;
import com.kuddle.auth.model.User;
import com.kuddle.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        String result = authService.register(request);

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        assertEquals("User registered successfully!", result);
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);

        String result = authService.login(request);
        assertEquals("Login successful!", result);
    }

    @Test
    void testLogin_Failure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("wronguser");
        request.setPassword("wrongpass");

        when(userRepository.findByUsername("wronguser")).thenReturn(null);

        String result = authService.login(request);
        assertEquals("Invalid credentials!", result);
    }
}
