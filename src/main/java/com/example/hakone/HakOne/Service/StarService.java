package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.UserAcademy.UserAcademy;
import com.example.hakone.HakOne.domain.UserAcademy.UserAcademyRepository;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        boolean isStarPresent = userAcademyRepository.findByMember_IdAndAcademy_Id(userId, academyId).isPresent();
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

    @Transactional(readOnly = true)
    public List<AllAcademyResDto> findAll(Long user_id, List<Academy> usersStarList) {
        List<Long> starAcademyIds = userAcademyRepository.findAcademyIdsByUserId(user_id);

        List<AllAcademyResDto> usersStarListResponse = usersStarList.stream()
                .map(academy -> {
                    AllAcademyResDto dto = new AllAcademyResDto();
                    dto.setAcademyId(academy.getId());
                    dto.setAcademyName(academy.getAcademyName());
                    dto.setRegion(academy.getRegion());
                    dto.setTel(academy.getTel());
                    dto.setAvgTuition(academy.getAvg_tuition());

                    List<Boolean> grades = new ArrayList<>();
                    grades.add(academy.isElem_grade());
                    grades.add(academy.isMid_grade());
                    grades.add(academy.isHigh_grade());
                    grades.add(academy.isElse_grade());
                    dto.setGradeList(grades);

                    List<Boolean> subjects = new ArrayList<>();
                    subjects.add(academy.isKor_class());
                    subjects.add(academy.isEng_class());
                    subjects.add(academy.isMath_class());
                    subjects.add(academy.isSoc_class());
                    subjects.add(academy.isSci_class());
                    subjects.add(academy.isFor_class());
                    subjects.add(academy.isEssay_class());
                    subjects.add(academy.isArt_class());
                    subjects.add(academy.isElse_class());
                    dto.setSubjectList(subjects);

                    dto.setStar(starAcademyIds.contains(academy.getId()));
                    dto.setReview_count(0); // 일단 구현 안해둬서 초기값으로 설정

                    return dto;
                })
                .collect(Collectors.toList());
        return usersStarListResponse;
    }
}
