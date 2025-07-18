package com.dulakshi.finsight.service;

import com.dulakshi.finsight.dto.LoginRequestDTO;
import com.dulakshi.finsight.dto.RegisterRequestDTO;
import com.dulakshi.finsight.entity.User;

public interface UserService {
    User register(RegisterRequestDTO requestDTO);
    User login(LoginRequestDTO requestDTO);
    void logout(User user);
    User getUserByUsername(String username);
}