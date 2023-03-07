package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.Food;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import com.example.hakone.HakOne.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final AcademyProcessor academyProcessor;
    private final ClassProcessor classProcessor;
    private final AcademyWriter academyWriter;
    private final ClassWriter classWriter;
    private static final int chunkSize = 1000;


    @Bean
    public Job csvFileItemReaderJob(){
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep1())
                .next(csvFileItemReaderStep2())
                .build();
    }

    public Step csvFileItemReaderStep1() {
        return stepBuilderFactory.get("csvFileItemReaderStep1")
                .<CsvDto, Academy>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .processor(academyProcessor)
                .writer(academyWriter)
                .build();
    }

    public Step csvFileItemReaderStep2() {
        return stepBuilderFactory.get("csvFileItemReaderStep2")
                .<CsvDto, Classroom>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .processor(classProcessor)
                .writer(classWriter)
                .build();
    }


//    @Bean
//    public CompositeItemProcessor csvProcessor() {
//        List<ItemProcessor> delegates = new ArrayList<>(2);
//        delegates.add(classProcessor);
//        delegates.add(academyProcessor);
//
//        CompositeItemProcessor processor = new CompositeItemProcessor();
//
//        processor.setDelegates(delegates);
//
//        return processor;
//    }
//
//    @Bean
//    public CompositeItemWriter csvWriter() {
//        List<ItemWriter> delegates = new ArrayList<>(2);
//        delegates.add(classWriter);
//        delegates.add(academyWriter);
//
//        CompositeItemWriter writer = new CompositeItemWriter();
//        writer.setDelegates(delegates);
//
//        return writer;
//    }
}
