package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.DeleteUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public DeleteUserResDto deleteUser(Long userId) {
        User currentUser = userRepository.findById(userId).get();
        String deletedEmail = currentUser.getEmail();
        userRepository.delete(currentUser);

        return DeleteUserResDto.builder()
                .message("탈퇴 처리가 완료되었습니다.")
                .email(deletedEmail)
                .build();
    }
}
