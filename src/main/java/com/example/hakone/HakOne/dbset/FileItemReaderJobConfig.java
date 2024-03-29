package com.example.hakone.HakOne.dbset;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import com.example.hakone.HakOne.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobOperator jobOperator;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final AcademyProcessor academyProcessor;
    private final ClassProcessor classProcessor;
    private final AcademyClassProcessor academyClassProcessor;
    private final AcademyWriter academyWriter;
    private final ClassWriter classWriter;
    private final AcademyClassWriter academyClassWriter;
    private static final int chunkSize = 100;


    @Bean
    public Job csvFileItemReaderJob() throws Exception {
        Set<JobExecution> jobExecutionsSet = jobExplorer.findRunningJobExecutions("csvFileItemReaderJob");
        for (JobExecution jobExecution:jobExecutionsSet) {
            if (jobExecution.getStatus()== BatchStatus.STARTED|| jobExecution.getStatus()== BatchStatus.STARTING || jobExecution.getStatus()== BatchStatus.STOPPING){
                jobOperator.stop(jobExecution.getId());
                System.out.println("###########Stopped#########");
            }
        }
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep1())
                .next(csvFileItemReaderStep2())
                .next(csvFileItemReaderStep3())
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

    public Step csvFileItemReaderStep3() {
        return stepBuilderFactory.get("csvFileItemReaderStep3")
                .<CsvDto, Academy>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .processor(academyClassProcessor)
                .writer(academyClassWriter)
                .build();
    }
}
