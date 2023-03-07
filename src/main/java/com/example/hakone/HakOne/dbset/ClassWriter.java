package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.classroom.ClassRepository;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class ClassWriter implements ItemWriter<Classroom> {
    private final ClassRepository classRepository;
    String className; //

    @Override
    public void write(List<? extends Classroom> list) throws Exception {
        className = list.get(0).getName(); //
        System.out.println("first className: "+className+" 저장 완료 ---");
        classRepository.saveAll(new ArrayList<Classroom>(list));
    }
}