package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}


