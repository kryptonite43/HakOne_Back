package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.UserService;
import com.example.hakone.HakOne.domain.LogDateTime;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.review.Review;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.DeleteUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AcademyRepository academyRepository;

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<DeleteUserResDto> deleteUser(@PathVariable Long user_id) {
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     회원 탈퇴");
        User currentUser = userRepository.findById(user_id).get();
        String deletedEmail = currentUser.getEmail();
        List<Academy> needToBeUpdatedAcademies = userService.deleteUser(user_id);
        for (Academy academyupdate : needToBeUpdatedAcademies) {
            System.out.println("리뷰 개수 수정해야 할 학원 출력하기");
            System.out.println("academy 이름: "+academyupdate.getAcademyName());
            academyupdate.updateAcademy(academyupdate);
            System.out.println("바뀐 academy reviewcount, avgscore: "+academyupdate.getReview_count()+", "+academyupdate.getAvg_score());
            academyRepository.save(academyupdate);
        }


         DeleteUserResDto deleteUserResDto = DeleteUserResDto.builder()
                .message("탈퇴 처리가 완료되었습니다.")
                .email(deletedEmail)
                .build();
         return ResponseEntity.ok(deleteUserResDto);
    }
}
