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
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private JobOperator jobOperator;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;


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
        stopJobExecution(1);
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



    // 중지할 JobExecution의 ID를 파라미터로 받는다.
    public void stopJobExecution(long jobExecutionId) throws Exception {
        // 현재 실행중인 JobExecution 객체를 조회한다.
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
        if (jobExecution != null) {
            // 해당 JobExecution의 JobInstance 객체를 조회한다.
            JobInstance jobInstance = jobExecution.getJobInstance();
            if (jobInstance != null) {
                // 트랜잭션 정의를 생성한다.
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    // 현재 실행중인 JobExecution을 중지시킨다.
                    jobOperator.stop(jobExecutionId);
                    // 해당 JobExecution의 상태를 STOPPING으로 변경한다.
                    jobExecution.setStatus(BatchStatus.STOPPING);
                    // JobExecution을 업데이트한다.
                    jobRepository.update(jobExecution);
                    // 트랜잭션을 커밋한다.
                    transactionManager.commit(status);
                } catch (Exception e) {
                    // 트랜잭션을 롤백한다.
                    transactionManager.rollback(status);
                    throw e;
                }
            }
        }
    }
}
