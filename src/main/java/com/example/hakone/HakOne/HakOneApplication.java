package com.example.hakone.HakOne;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class HakOneApplication {
	public static void main(String[] args) {
		SpringApplication.run(HakOneApplication.class, args);
	}

}
