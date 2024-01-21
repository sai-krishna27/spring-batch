package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.SecondTasklet;

@Configuration
public class SampleBatch {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	SecondTasklet secondTasklet;
	
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("first job")
				.incrementer(new RunIdIncrementer())
				.start(firstStep())  //for very first step
				.next(secondStep())
				.build();
		
	}
	
	private Step firstStep() {
		return stepBuilderFactory.get("first step")
				.tasklet(firstTasklet())
				.build();
	}
	
	private Tasklet firstTasklet() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("first tasklet step");
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	private Step secondStep() {
		return stepBuilderFactory.get("first step")
				.tasklet(secondTasklet)
				.build();
	}
	
//	private Tasklet secondTasklet() {
//		return new Tasklet() {
//			
//			@Override
//			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//				System.out.println("seconds tasklet step");
//				return RepeatStatus.FINISHED;
//			}
//		};
//	}
}

