package com.dulakshi.finsight.controller;

import com.dulakshi.finsight.dto.LoginRequestDTO;
import com.dulakshi.finsight.dto.RegisterRequestDTO;
import com.dulakshi.finsight.entity.User;
import com.dulakshi.finsight.service.UserService;
import com.dulakshi.finsight.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO requestDTO) {
        try {
            User user = userService.register(requestDTO);

            if(user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration not success! Please try again later.");
            } else {
                return ResponseEntity.ok("Registration successful!");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        try {
            User user = userService.login(requestDTO);

            if(user != null) {
                String token = jwtUtil.generateToken(user.getUsername());

                return ResponseEntity.ok(Map.of("token", token, "message", "Login successful!"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getUsername(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userService.getUserByUsername(userDetails.getUsername()).getUsername();
        return ResponseEntity.ok(username);
    }
}