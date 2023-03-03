package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Food;
import com.example.hakone.HakOne.domain.academy.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Food> {
    private final FoodRepository foodRepository;

    @Override
    public void write(List<? extends Food> list) throws Exception {
        foodRepository.saveAll(new ArrayList<Food>(list));
    }
}
