package com.workflow.backend.service.impl;

import com.workflow.backend.dto.LoginRequest;
import com.workflow.backend.dto.LoginResponse;
import com.workflow.backend.model.User;
import com.workflow.backend.repository.UserRepository;
import com.workflow.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户名或密码错误");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        String token = "token_" + user.getId() + "_" + System.currentTimeMillis();

        return new LoginResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }
}
