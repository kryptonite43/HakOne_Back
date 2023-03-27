package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AcademyWriter implements ItemWriter<Academy> {
    private final AcademyRepository academyRepository;

    @Override
    public void write(List<? extends Academy> list) throws Exception {
        for (Academy academy : list) {
            Optional<Academy> temp = academyRepository.findByTelAndAcademyNameAndRegion(academy.getTel(), academy.getAcademyName(), academy.getRegion());
            if (temp.isEmpty()) { // 학원이 존재하지 않을 때만 신규 저장
                academyRepository.save(academy);
            }
        }
    }
}
