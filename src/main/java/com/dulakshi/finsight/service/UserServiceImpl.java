package com.dulakshi.finsight.service;

import com.dulakshi.finsight.dto.LoginRequestDTO;
import com.dulakshi.finsight.dto.RegisterRequestDTO;
import com.dulakshi.finsight.entity.SecurityLog;
import com.dulakshi.finsight.entity.User;
import com.dulakshi.finsight.exception.ResourceNotFoundException;
import com.dulakshi.finsight.exception.UserAlreadyExistsException;
import com.dulakshi.finsight.repository.SecurityLogRepository;
import com.dulakshi.finsight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityLogRepository securityLogRepository;

    @Override
    public User register(RegisterRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already registered.");
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        SecurityLog securityLog = new SecurityLog();
        securityLog.setUser(null);
        securityLog.setTimestamp(LocalDateTime.now());

        try {
            User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash())) {
                throw new IllegalArgumentException("Invalid credentials");
            }

            securityLog.setAction("100: LOGIN_SUCCESS");
            securityLog.setUser(user);
            securityLogRepository.save(securityLog);

            return user;
        } catch (ResourceNotFoundException ex1) {
            securityLog.setAction("101: USER_NOT_FOUND");
            securityLogRepository.save(securityLog);
            throw ex1;
        } catch (IllegalArgumentException ex2) {
            securityLog.setAction("102: UNAUTHORIZED_ACCESS");
            securityLogRepository.save(securityLog);
            throw ex2;
        }
    }

    @Override
    public void logout(User user) {
        SecurityLog securityLog = new SecurityLog();
        securityLog.setUser(user);
        securityLog.setTimestamp(LocalDateTime.now());

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            securityLog.setAction("103: REQUEST_SIGN_OUT_SUCCESS");
            securityLogRepository.save(securityLog);
        } else {
            securityLog.setAction("104: REQUEST_SIGN_OUT_FAILED");
            securityLogRepository.save(securityLog);
            throw new ResourceNotFoundException("User not found! Logout request failed");
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}