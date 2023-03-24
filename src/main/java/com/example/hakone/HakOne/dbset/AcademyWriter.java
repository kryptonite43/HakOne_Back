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
            if (temp.isPresent()) { // 학원이 존재하면 -> 저장된 학원(temp)이랑 신규학원(academy)이랑 같은 학원인지 확인. 같은 학원이 아닐 때만 저장
                long lastAcademyId = academyRepository.count();
                if (lastAcademyId != 0) {
                    Academy lastSavedAcademy = academyRepository.findById(lastAcademyId).get();
                    if (!lastSavedAcademy.getAcademyName().equals(academy.getAcademyName())) {
                        academyRepository.save(academy);
                    }
                }
                else {
                    academyRepository.save(academy);
                }
            }
            else {
                academyRepository.save(academy);
            }
        }
    }
}
