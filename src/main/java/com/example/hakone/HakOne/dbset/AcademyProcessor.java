package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AcademyProcessor implements ItemProcessor<CsvDto, Academy> {
    @Override
    public Academy process(CsvDto item) throws Exception {
        Academy result = new Academy();

        // 학원 정보 추출
        result.setAcademyName(item.getAcademyName());
        result.setRegion(item.getRegion());
        result.setAddress(item.getAddress());
        result.setTel(item.getTel());
        result.setTeacher(item.getTeacher());

        result.setElem_grade(item.isElem_grade());
        result.setMid_grade(item.isMid_grade());
        result.setHigh_grade(item.isHigh_grade());
        result.setElse_grade(item.isElse_grade());

        return result;
    }
}
