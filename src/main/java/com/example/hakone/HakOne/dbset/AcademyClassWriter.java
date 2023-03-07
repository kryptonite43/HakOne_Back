package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AcademyClassWriter implements ItemWriter<Academy> {
    private final AcademyRepository academyRepository;
    @Override
    public void write(List<? extends Academy> list) throws Exception {
        academyRepository.saveAll(new ArrayList<Academy>(list));
    }
}
