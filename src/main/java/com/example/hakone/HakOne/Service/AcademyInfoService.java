package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import com.example.hakone.HakOne.dto.SpecificAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AcademyInfoService {

    private final AcademyRepository academyRepository;

    @Transactional(readOnly = true)
    public List<AllAcademyResDto> findAllDesc() {
        return academyRepository.findAllDesc().stream()
                .map(AllAcademyResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SpecificAcademyResDto findById(Long id) {
        Academy academy = academyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 학원이 없습니다. id=" + id)); // 수정 예정
        return new SpecificAcademyResDto(academy);
    }
}


