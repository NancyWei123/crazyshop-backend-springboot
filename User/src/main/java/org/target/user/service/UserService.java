package org.target.user.service;

import org.target.user.dto.LoginRequest;
import org.target.user.dto.LoginResponse;
import org.target.user.dto.RegisterRequest;
import org.target.user.dto.UserDTO;

public interface UserService {
    UserDTO register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserDTO getById(Long id);
}