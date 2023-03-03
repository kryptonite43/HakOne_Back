package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Food;
import org.springframework.batch.item.ItemProcessor;

public class CsvProcessor implements ItemProcessor<Food, Food> {
    @Override
    public Food process(Food item) throws Exception { // public Food->writer에게 보낼 타입, process (Food->reader에서 받을 타입 item)
        return item;
    }
}
