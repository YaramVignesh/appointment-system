package com.vignesh01.appointment_system.controller;

import com.vignesh01.appointment_system.dto.LoginRequest;
import com.vignesh01.appointment_system.dto.LoginResponse;
import com.vignesh01.appointment_system.dto.RegisterRequest;
import com.vignesh01.appointment_system.entity.Client;
import com.vignesh01.appointment_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@Valid @RequestBody RegisterRequest registerRequest) {
        Client newClient = authService.registerClient(registerRequest);

        return new ResponseEntity<>(newClient,HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
