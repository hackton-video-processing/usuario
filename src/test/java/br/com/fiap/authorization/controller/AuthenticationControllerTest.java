package br.com.fiap.authorization.controller;

import br.com.fiap.authorization.dto.request.AuthenticationRequest;
import br.com.fiap.authorization.dto.request.RegisterRequest;
import br.com.fiap.authorization.dto.request.TokenRequest;
import br.com.fiap.authorization.dto.response.AuthenticationResponse;
import br.com.fiap.authorization.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Converts objects to JSON

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "12345678900", "john.doe@example.com", "password");
        AuthenticationResponse mockResponse = new AuthenticationResponse("mock-token");

        Mockito.when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("user@example.com", "password123");
        AuthenticationResponse mockResponse = new AuthenticationResponse("mock-token");

        Mockito.when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)));

        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }

    @Test
    public void testValidateToken() throws Exception {
        TokenRequest tokenRequest = new TokenRequest("mock-token");

        Mockito.when(authenticationService.validate(any(TokenRequest.class))).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/api/v1/auth/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenRequest)));

        result.andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
