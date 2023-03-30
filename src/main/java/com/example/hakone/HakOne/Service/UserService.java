package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.review.Review;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.DeleteUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AcademyRepository academyRepository;
    @Transactional
    public List<Academy> deleteUser(Long userId) {
        User currentUser = userRepository.findById(userId).get();

        List<Review> needToBeDeletedReviews = currentUser.getReviews();
        List<Academy> needToBeUpdatedAcademies = new ArrayList<>();
        for (Review review : needToBeDeletedReviews) {
            Academy academy = review.getAcademy();
            System.out.println("* need to be deleted - academy: "+ academy.getAcademyName());
            needToBeUpdatedAcademies.add(academy);
        }
        userRepository.delete(currentUser);

        return needToBeUpdatedAcademies;

//        return DeleteUserResDto.builder()
//                .message("탈퇴 처리가 완료되었습니다.")
//                .email(deletedEmail)
//                .build();
    }
}
