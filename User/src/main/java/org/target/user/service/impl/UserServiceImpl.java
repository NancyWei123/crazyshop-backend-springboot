package org.target.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.target.user.dto.LoginRequest;
import org.target.user.dto.LoginResponse;
import org.target.user.dto.RegisterRequest;
import org.target.user.dto.UserDTO;
import org.target.user.entity.User;
import org.target.user.repository.UserRepository;
import org.target.user.service.EmailService;
import org.target.user.service.UserService;
import org.target.user.service.VerificationCodeService;
import org.target.user.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final VerificationCodeService verificationCodeService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void sendVerificationCode(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String code = emailService.generateCode();

        verificationCodeService.saveCode(email, code);

        emailService.sendVerificationCode(email, code);
    }

    @Override
    public UserDTO register(RegisterRequest request) {
        if (!verificationCodeService.verifyCode(request.getEmail(), request.getCode())) {
            throw new RuntimeException("Invalid or expired verification code");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepository.save(user);

        return UserDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = JwtUtil.generateToken(user.getId(), user.getEmail());

        return new LoginResponse(token, user.getId(), user.getUsername());
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}