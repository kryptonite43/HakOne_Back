package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.academy.Food;
import com.example.hakone.HakOne.domain.academy.FoodRepository;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AcademyWriter implements ItemWriter<Academy> {
    private final AcademyRepository academyRepository;
    String academyName;
    @Override
    public void write(List<? extends Academy> list) throws Exception {
        int i = 0;
        for (Academy academy : list) {
            Optional<Academy> temp = academyRepository.findByAcademyName(academy.getAcademyName());
            if (!temp.isPresent()) { // 없을 때만 저장
                academyRepository.save(academy);
                academyName = academy.getAcademyName();
                //System.out.println("index: "+i+", academyName: "+academyName+" 저장 완료 ---");
            }
            else {
                //System.out.println("index: "+i+", academyName: "+academyName+", 중복입니다");
            }

        }
//        academyRepository.saveAll(new ArrayList<Academy>(list));
    }
}
