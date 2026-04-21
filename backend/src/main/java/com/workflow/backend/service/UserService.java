package com.workflow.backend.service;

import com.workflow.backend.dto.LoginRequest;
import com.workflow.backend.dto.LoginResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);
}
