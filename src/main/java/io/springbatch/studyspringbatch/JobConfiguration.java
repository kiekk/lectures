package io.springbatch.studyspringbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {

                    JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();

                    System.out.println(jobParameters.getString("name"));
                    System.out.println(jobParameters.getLong("seq"));
                    System.out.println(jobParameters.getDate("date"));
                    System.out.println(jobParameters.getDouble("age"));

                    Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

                    System.out.println(jobParameters1.get("name"));
                    System.out.println(jobParameters1.get("seq"));
                    System.out.println(jobParameters1.get("date"));
                    System.out.println(jobParameters1.get("age"));
                    
                    // jobParameters 와 jobParameters1 은 return type 이 다름

                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 was executed");
                    // 실패한 job instance 는 여러 번 실행 가능
                    // 단, 성공한 이후에는 동일한 job instance 는 실행 불가능
                    throw new RuntimeException("step2 has failed");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
