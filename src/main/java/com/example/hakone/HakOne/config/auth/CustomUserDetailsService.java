package com.example.hakone.HakOne.config.auth;

import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.GoogleUserInfoDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        System.out.println("--------loadUserByUserName-----");
        System.out.println(user.getName()+", "+user.getEmail()+", "+user.getProfile_pic());
//        GoogleUserInfoDto googleAuthUser = new GoogleUserInfoDto(
//                user.getId().toString(),
//                user.getEmail(),
//                true,
//                user.getName(),
//                "",
//                "",
//                user.getProfile_pic(),
//                ""
//        );
//        googleAuthUser.setName(user.getName());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), "", Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())));


    }


}