package com.nckhntu.eventunivercity_v2_be.Service;

import com.nckhntu.eventunivercity_v2_be.Entity.Users;
import com.nckhntu.eventunivercity_v2_be.Model.Request.RegisterRequest;

public interface UserService {
    void registerUser(RegisterRequest registerRequest);
    void emailVerification(String email, String verificationCode);
    void resendVerificationCode(String email);
}
