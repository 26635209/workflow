package com.workflow.backend.config;

import com.workflow.backend.model.User;
import com.workflow.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode("123456");
            
            User user = new User();
            user.setUsername("admin");
            user.setPassword(hashedPassword);
            user.setEmail("admin@example.com");
            user.setCreatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            System.out.println("测试用户已创建: admin / 123456");
        }
    }
}
