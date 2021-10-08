package com.example.apipokemon;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration(exclude={BatchAutoConfiguration.class})
@ComponentScan({"com.example.apipokemon"})
@EnableJpaRepositories({"com.example.apipokemon.repository"})
@EntityScan({"com.example.apipokemon.model"})
@EnableSwagger2
@EnableScheduling
public class ApiPokemonApplication {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	public static void main(String[] args) {
		SpringApplication.run(ApiPokemonApplication.class, args);
	}

	//@Scheduled(cron = "0 */1 * * * ?")
	@PostConstruct
	public void perform() throws Exception
	{
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		JobExecution execution=jobLauncher.run(job, params);
		System.out.println("STATUS :: "+execution.getStatus());
	}
}
