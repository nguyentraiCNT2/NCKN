package com.nckhntu.eventunivercity_v2_be.Controller;

import com.nckhntu.eventunivercity_v2_be.Configuration.Security.CustomUserDetailsService;
import com.nckhntu.eventunivercity_v2_be.Configuration.Security.JwtTokenUtil;
import com.nckhntu.eventunivercity_v2_be.Model.Request.LoginRequest;
import com.nckhntu.eventunivercity_v2_be.Model.Request.RegisterRequest;
import com.nckhntu.eventunivercity_v2_be.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthController(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            boolean ismath = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
            if (!ismath)
                return new ResponseEntity<>("Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED);
            String token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage(), "status", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return new ResponseEntity<>("Đăng ký thành công vui lòng xác thực tài khoản", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage(), "status", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
