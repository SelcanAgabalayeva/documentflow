package com.selcan.documentflow.controllers;

import com.selcan.documentflow.dtos.auth.AuthResponse;
import com.selcan.documentflow.dtos.auth.LoginDto;
import com.selcan.documentflow.dtos.auth.RegisterDto;
import com.selcan.documentflow.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','APPROVER')")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token not found");
        }

        String token = authHeader.substring(7);

        authService.logout(token);
    }
}
