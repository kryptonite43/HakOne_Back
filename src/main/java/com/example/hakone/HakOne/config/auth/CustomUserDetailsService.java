package com.example.hakone.HakOne.config.auth;

import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(@Lazy UserRepository userRepository) { // 스프링 빈 순환참조 문제에 대한 임시 방편 @Lazy
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        User user = result.get();
        System.out.println("result: "+user.getName()+", "+user.getEmail()+", "+user.getProfile_pic());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), "", Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())));
    }
}