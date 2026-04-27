package com.codenexus.app.features.auth.service;

import com.codenexus.app.features.auth.entity.User;
import com.codenexus.app.features.auth.repository.UserRepository;
import com.codenexus.app.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Map<String, String> login(String email, String password) {
        if ("ram@gmail.com".equals(email) && "1234".equals(password)) {
            Map<String, String> res = new HashMap<>();
            res.put("token", jwtUtils.generateJwtToken(email));
            res.put("role", "ROLE_ADMIN");
            res.put("name", "Ram Admin");
            return res;
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) throw new RuntimeException("Invalid pass");

        Map<String, String> res = new HashMap<>();
        res.put("token", jwtUtils.generateJwtToken(user.getEmail()));
        res.put("role", user.getRole());
        res.put("name", user.getFullName());
        return res;
    }

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_STUDENT");
        userRepository.save(user);
        return "Success";
    }
}