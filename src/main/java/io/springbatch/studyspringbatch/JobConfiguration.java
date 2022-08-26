package io.springbatch.studyspringbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {

                    int i = 0;

                    @Override
                    public String read() throws SkippableException {
                        i++;

                        if (i == 3) {
                            throw new SkippableException("skip");
                        }

                        System.out.printf("ItemReader : %d\n", i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(SkippableException.class)
                .skipLimit(4)
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> itemProcessor() {
        return new SkipItemProcessor();
    }

    @Bean
    public ItemWriter<? super String> itemWriter() {
        return new SkipItemWriter();
    }

}
