package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.UserAcademy.UserAcademy;
import com.example.hakone.HakOne.domain.UserAcademy.UserAcademyRepository;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        boolean isStarPresent = userAcademyRepository.findByMember_IdAndAcademy_Id(userId, academyId).isPresent();
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

    @Transactional
    public Boolean deleteStar(Long userId, Long academyId) {
        Boolean isStarPresent = userAcademyRepository.findByMember_IdAndAcademy_Id(userId, academyId).isPresent();
        if (isStarPresent) {
            UserAcademy userAcademy = userAcademyRepository.findByMember_IdAndAcademy_Id(userId, academyId).get();
            userAcademyRepository.delete(userAcademy);

            return true;
        }
        else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Academy> findAllByMember_Id(Long userId) {
        List<UserAcademy> userAcademyList = userAcademyRepository.findAllByMember_Id(userId);
        List<Academy> starAcademyList = userAcademyList.stream()
                .map(UserAcademy::getAcademy)
                .collect(Collectors.toList());

        return starAcademyList;
    }
}
