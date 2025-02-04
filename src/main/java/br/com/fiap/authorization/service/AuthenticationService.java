package br.com.fiap.authorization.service;

import br.com.fiap.authorization.dto.request.AuthenticationRequest;
import br.com.fiap.authorization.dto.request.RegisterRequest;
import br.com.fiap.authorization.dto.request.TokenRequest;
import br.com.fiap.authorization.dto.response.AuthenticationResponse;
import br.com.fiap.authorization.dto.user.Role;
import br.com.fiap.authorization.entity.User;
import br.com.fiap.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .cpf(registerRequest.getCpf())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = repository.save(user);

        String jwtToken = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = repository.findByEmail(authenticationRequest.getEmail())
                .filter(userFounded -> passwordEncoder.matches(authenticationRequest.getPassword(), userFounded.getPassword()))
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Boolean validate(TokenRequest tokenRequest) {

        return Optional.of(tokenRequest.getToken())
                .map(jwtService::extractSub)
                .map(emailAsSub -> repository.findByEmail(emailAsSub)
                        .orElseThrow())
                .map(userFounded -> jwtService.isTokenValid(tokenRequest.getToken(), userFounded))
                .orElse(false);
    }
}
