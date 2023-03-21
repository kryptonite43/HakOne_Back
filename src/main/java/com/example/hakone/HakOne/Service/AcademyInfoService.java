package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.UserAcademy.UserAcademyRepository;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import com.example.hakone.HakOne.dto.SpecificAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AcademyInfoService {

    private final AcademyRepository academyRepository;
    private final UserAcademyRepository userAcademyRepository;

    @Transactional(readOnly = true)
    public List<AllAcademyResDto> findAllDesc(Long userId) {
        List<Long> starAcademyIds = userAcademyRepository.findAcademyIdsByUserId(userId);

        return academyRepository.findAllDesc().stream()
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
                    dto.setAvg_score(0); // 일단 구현 안해둬서 초기값으로 설정
                    dto.setReview_count(0); // 일단 구현 안해둬서 초기값으로 설정

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SpecificAcademyResDto findById(Long user_id, Long academy_id) {
        List<Long> starAcademyIds = userAcademyRepository.findAcademyIdsByUserId(user_id); // 더 간단하게 수정 - userid, academyid 정보로 한번에 star 판별
        Academy academy = academyRepository.findById(academy_id).get();

        SpecificAcademyResDto specificAcademyResDto = new SpecificAcademyResDto(academy);
        specificAcademyResDto.setStar(starAcademyIds.contains(academy.getId()));
        return specificAcademyResDto;
    }
}


