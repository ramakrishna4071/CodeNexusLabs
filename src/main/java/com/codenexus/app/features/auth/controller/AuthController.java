package com.codenexus.app.features.auth.controller;

import com.codenexus.app.common.dto.ApiResponse;
import com.codenexus.app.features.auth.entity.User;
import com.codenexus.app.features.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody User user) {
        return ApiResponse.success(authService.register(user), "Registration Successful");
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        return ApiResponse.success(
            authService.login(credentials.get("email"), credentials.get("password")), 
            "Login Successful"
        );
    }
}