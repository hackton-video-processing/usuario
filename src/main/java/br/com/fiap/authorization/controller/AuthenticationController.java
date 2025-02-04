package br.com.fiap.authorization.controller;

import br.com.fiap.authorization.dto.request.AuthenticationRequest;
import br.com.fiap.authorization.dto.request.TokenRequest;
import br.com.fiap.authorization.dto.response.AuthenticationResponse;
import br.com.fiap.authorization.dto.request.RegisterRequest;
import br.com.fiap.authorization.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(service.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(service.authenticate(authenticationRequest));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(service.validate(tokenRequest));
    }


}
