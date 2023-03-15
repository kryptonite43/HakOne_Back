package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.UserAcademy.UserAcademy;
import com.example.hakone.HakOne.domain.UserAcademy.UserAcademyRepository;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.user.Role;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import com.example.hakone.HakOne.dto.SpecificAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StarService {
    private final UserAcademyRepository userAcademyRepository;
    private final AcademyRepository academyRepository;
    private final UserRepository userRepository;
    @Transactional
    public Boolean addStar(Long userId, Long academyId) {
        User member = userRepository.findById(userId).get();
        Academy academy = academyRepository.findById(academyId).get();
        Boolean isStarPresent = userAcademyRepository.findByMember_IdAndAcademy_Id(userId, academyId).isPresent();
        if (isStarPresent) {
            return false;
        }
        else {
            UserAcademy userAcademy = UserAcademy.builder()
                    .member(member)
                    .academy(academy)
                    .build();
            userAcademyRepository.save(userAcademy);

            return true;
        }
    }

//    @Transactional(readOnly = true)
//    public Boolean findById(Long starId) {
//        Academy academy = academyRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 학원이 없습니다. id=" + id));
//        return new SpecificAcademyResDto(academy);
//    }
}
