package com.selcan.documentflow.service.impls;

import com.selcan.documentflow.dtos.auth.AuthResponse;
import com.selcan.documentflow.dtos.auth.LoginDto;
import com.selcan.documentflow.dtos.auth.RegisterDto;
import com.selcan.documentflow.dtos.auth.UserResponse;
import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.Role;
import com.selcan.documentflow.exceptions.EmailAlreadyExistsException;
import com.selcan.documentflow.exceptions.UserNotFoundException;
import com.selcan.documentflow.repositories.UserRepository;
import com.selcan.documentflow.security.JwtUtil;
import com.selcan.documentflow.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService blacklistService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JwtBlacklistService blacklistService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.blacklistService = blacklistService;
    }

    @Override
    public AuthResponse register(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(mapToResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        blacklistService.blacklist(token, 86400000);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
