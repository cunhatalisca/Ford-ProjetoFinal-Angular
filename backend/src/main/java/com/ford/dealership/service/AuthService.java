package com.ford.dealership.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ford.dealership.dto.AuthRequest;
import com.ford.dealership.dto.AuthResponse;
import com.ford.dealership.dto.RegisterRequest;
import com.ford.dealership.dto.UserDTO;
import com.ford.dealership.entity.User;
import com.ford.dealership.exception.BusinessException;
import com.ford.dealership.repository.UserRepository;
import com.ford.dealership.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String DEFAULT_ROLE = "usuario";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ja existe um usuario com este e-mail.");
        }
        User user = User.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(DEFAULT_ROLE)
                .checkboxTermos(request.getCheckboxTermos())
                .checkboxNewsLetter(request.getCheckboxNewsLetter())
                .build();
        userRepository.save(user);
        return buildResponse(user);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new BusinessException("E-mail ou senha invalidos."));
        return buildResponse(user);
    }

    private AuthResponse buildResponse(User user) {
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, UserDTO.from(user));
    }
}
