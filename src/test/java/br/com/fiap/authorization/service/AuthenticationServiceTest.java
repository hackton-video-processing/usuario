package br.com.fiap.authorization.service;

import br.com.fiap.authorization.dto.request.AuthenticationRequest;
import br.com.fiap.authorization.dto.request.RegisterRequest;
import br.com.fiap.authorization.dto.request.TokenRequest;
import br.com.fiap.authorization.dto.response.AuthenticationResponse;
import br.com.fiap.authorization.dto.user.Role;
import br.com.fiap.authorization.entity.User;
import br.com.fiap.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;
    private TokenRequest tokenRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("John", "Doe", "12345678900", "john.doe@example.com", "password");
        authenticationRequest = new AuthenticationRequest("john.doe@example.com", "password");
        tokenRequest = new TokenRequest("validToken");

        user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .cpf("12345678900")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldRegisterUserAndReturnToken() {
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    void shouldAuthenticateUserAndReturnToken() {
        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).findByEmail(authenticationRequest.getEmail());
        verify(jwtService).generateToken(user);
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        when(jwtService.extractSub(tokenRequest.getToken())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(tokenRequest.getToken(), user)).thenReturn(true);

        Boolean isValid = authenticationService.validate(tokenRequest);

        assertTrue(isValid);
        verify(jwtService).extractSub(tokenRequest.getToken());
        verify(userRepository).findByEmail(user.getEmail());
        verify(jwtService).isTokenValid(tokenRequest.getToken(), user);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        when(jwtService.extractSub(tokenRequest.getToken())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(tokenRequest.getToken(), user)).thenReturn(false);

        Boolean isValid = authenticationService.validate(tokenRequest);

        assertFalse(isValid);
        verify(jwtService).extractSub(tokenRequest.getToken());
        verify(userRepository).findByEmail(user.getEmail());
        verify(jwtService).isTokenValid(tokenRequest.getToken(), user);
    }
}
