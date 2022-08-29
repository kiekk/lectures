//package io.springbatch.studyspringbatch.api;
//
//import io.springbatch.studyspringbatch.RetryableException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.RetryPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Configuration
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job job() throws Exception {
//        return jobBuilderFactory.get("batchJob1")
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//                .<String, String>chunk(5)
//                .reader(reader())
//                .processor(processor())
//                .writer(items -> items.forEach(System.out::println))
//                .faultTolerant()
//                .skip(RetryableException.class)
//                .skipLimit(2)
//                .retryPolicy(retryPolicy())
//                .build();
//    }
//
//    @Bean
//    public ListItemReader<String> reader() {
//        return new ListItemReader<>(
//                IntStream
//                        .range(0, 30)
//                        .mapToObj(String::valueOf)
//                        .collect(Collectors.toList()));
//        /*
//        range: start <= value < end
//        rangeClosed: start <= value <= end
//         */
//    }
//
//    @Bean
//    public ItemProcessor<String, String> processor() {
//        return new RetryItemProcessor();
//    }
//
//    @Bean
//    public RetryPolicy retryPolicy() {
//        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
//        exceptionClass.put(RetryableException.class, true);
//
//        return new SimpleRetryPolicy(2, exceptionClass);
//    }
//}
