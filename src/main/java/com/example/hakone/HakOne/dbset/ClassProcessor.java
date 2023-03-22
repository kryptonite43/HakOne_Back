package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import com.example.hakone.HakOne.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ClassProcessor implements ItemProcessor<CsvDto, Classroom> {
    @Autowired
    AcademyRepository academyRepository;
    @Override
    public Classroom process(CsvDto item) throws Exception {
        Classroom result = new Classroom();

        result.setName(item.getClassName());
        result.setTuition(item.getMonth_tuition());
        result.setTel(item.getTel());
        result.setRegion(item.getRegion());

        result.setKor_class(item.isKor_class());
        result.setEng_class(item.isEng_class());
        result.setMath_class(item.isMath_class());
        result.setSoc_class(item.isSoc_class());
        result.setSci_class(item.isSci_class());
        result.setFor_class(item.isFor_class());
        result.setEssay_class(item.isEssay_class());
        result.setArt_class(item.isArt_class());
        result.setElse_class(item.isElse_class());

        Optional<Academy> academy = academyRepository.findByTelAndAcademyNameAndRegion(result.getTel(),item.getAcademyName(), result.getRegion());
        result.setAcademy(academy.get());
        return result;
    }
}
