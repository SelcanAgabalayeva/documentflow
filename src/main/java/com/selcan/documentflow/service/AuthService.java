package com.selcan.documentflow.service;

import com.selcan.documentflow.dtos.auth.AuthResponse;
import com.selcan.documentflow.dtos.auth.LoginDto;
import com.selcan.documentflow.dtos.auth.RegisterDto;

public interface AuthService {
    AuthResponse register(RegisterDto request);
    AuthResponse login(LoginDto request);
    void logout(String token);
}
