package com.nckhntu.eventunivercity_v2_be.Configuration.Security;

import com.nckhntu.eventunivercity_v2_be.Entity.Users;
import com.nckhntu.eventunivercity_v2_be.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));

        if (!user.getActive())
            throw new RuntimeException("Tài khoản này đã bị khóa");

        if (!user.getEmailVerified())
            throw new RuntimeException("Chưa xác thực tài khoản");

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
