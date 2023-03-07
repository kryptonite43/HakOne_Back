package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import com.example.hakone.HakOne.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AcademyClassProcessor implements ItemProcessor<CsvDto, Academy> {
    @Autowired
    AcademyRepository academyRepository;
    @Override
    public Academy process(CsvDto item) throws Exception { // 반 정보 추출 및 수강료 평균 계산, 과목 정보 합산
        Academy result = academyRepository.findByAcademyName(item.getAcademyName()).get();
        List<Classroom> classList = result.getClassroomList();
        List<Boolean> subjectList = new ArrayList<>(); // {국어, 영어, 수학, 사회, 과학, 외국어, 논술, 예능, 기타}
        int k = 0;

        while (k < 9) { // 과목리스트 초기화
            subjectList.add(false);
            k++;
        }
        boolean kor = subjectList.get(0);
        boolean eng = subjectList.get(1);
        boolean math = subjectList.get(2);
        boolean soc = subjectList.get(3);
        boolean sci = subjectList.get(4);
        boolean foreign = subjectList.get(5);
        boolean essay = subjectList.get(6);
        boolean art = subjectList.get(7);
        boolean elseclass = subjectList.get(8);

        if (classList != null && !classList.isEmpty()) {
            int totalPrice = 0;
            int count = 0;
            for (Classroom classroom : classList) {
                int tuition = classroom.getTuition();
                totalPrice += tuition;
                int i = 0;
                while (i < 9) {
                    kor |= classroom.isKor_class();
                    eng |= classroom.isEng_class();
                    math |= classroom.isMath_class();
                    soc |= classroom.isSoc_class();
                    sci |= classroom.isSci_class();
                    foreign |= classroom.isFor_class();
                    essay |= classroom.isEssay_class();
                    art |= classroom.isArt_class();
                    elseclass |= classroom.isElse_class();
                    i++;
                }
                count++;
            }
            if (count > 0) {
                int averageTuition = totalPrice / count;
                result.setAvg_tuition(averageTuition);
                result.setKor_class(kor);
                result.setEng_class(eng);
                result.setMath_class(math);
                result.setSoc_class(soc);
                result.setSci_class(sci);
                result.setFor_class(foreign);
                result.setEssay_class(essay);
                result.setArt_class(art);
                result.setElse_class(elseclass);
            }
        }
        // 별점 점수 관련 구현 예정
        return result;
    }
}
