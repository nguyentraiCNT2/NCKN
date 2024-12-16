package com.nckhntu.eventunivercity_v2_be.Service.IMPL;

import com.nckhntu.eventunivercity_v2_be.Entity.Users;
import com.nckhntu.eventunivercity_v2_be.Model.Request.RegisterRequest;
import com.nckhntu.eventunivercity_v2_be.Repository.OTPRepository;
import com.nckhntu.eventunivercity_v2_be.Repository.OTPTemplateRepository;
import com.nckhntu.eventunivercity_v2_be.Repository.UserRepository;
import com.nckhntu.eventunivercity_v2_be.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserServiceIMPL implements UserService {
    private final UserRepository userRepository;
    private final OTPRepository otpRepository;
    private final OTPTemplateRepository otpTemplateRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceIMPL(UserRepository userRepository, OTPRepository otpRepository, OTPTemplateRepository otpTemplateRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {



        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.otpTemplateRepository = otpTemplateRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        try {
            boolean isEmail = userRepository.existsByEmail(registerRequest.getEmail());
            boolean isUsername = userRepository.existsByUsername(registerRequest.getUsername());
            if (isEmail)
                throw new RuntimeException("Email đã được sử dụng");
            if (isUsername)
                throw new RuntimeException("tên đăng nhập này đã được sử dụng");
            if (registerRequest.getPassword().length() < 6)
                throw new RuntimeException("Mật khẩu có tối thiểu 6 ký tự");
            if (registerRequest.getPassword().length() > 255)
                 throw new RuntimeException("Mật khẩu có tối đa 255 ký tự");
            if (registerRequest.getPassword().contains(" "))
                throw new RuntimeException("Mật khẩu không thể chứa ký tự trống");
            Users users  = new Users();
            users.setEmail(registerRequest.getEmail());
            users.setUsername(registerRequest.getUsername());
            users.setFirstName(registerRequest.getFirstName());
            users.setLastName(registerRequest.getLastName());
            users.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            users.setGender(registerRequest.getGender());
            users.setBirthDate(registerRequest.getBirthDate());
            users.setActive(true);
            users.setEmailVerified(false);
            users.setName(registerRequest.getFirstName() + " " + registerRequest.getLastName());
            users.setRole("USER");
            users.setCreatedAt(Timestamp.from(Instant.now()));
            userRepository.save(users);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void emailVerification(String email, String verificationCode) {

    }

    @Override
    public void resendVerificationCode(String email) {

    }
}

