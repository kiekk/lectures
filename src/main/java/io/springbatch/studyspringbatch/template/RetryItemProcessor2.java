package io.springbatch.studyspringbatch.template;

import io.springbatch.studyspringbatch.RetryableException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;

public class RetryItemProcessor2 implements ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplate;

    private int cnt = 0;

    @Override
    public Customer process(String item) throws Exception {

        return retryTemplate.execute(context -> {
            cnt++;

            if (item.equals("1") || item.equals("2")) {
                throw new RetryableException(String.format("failed cnt : %d", cnt));
            }

            return new Customer(item);
        }, context -> new Customer(item));
    }
}
