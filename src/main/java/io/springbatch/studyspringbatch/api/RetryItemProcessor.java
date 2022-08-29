package io.springbatch.studyspringbatch.api;

import io.springbatch.studyspringbatch.RetryableException;
import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {

        if (item.equals("2") || item.equals("3")) {
            cnt++;
            throw new RetryableException(String.format("failed cnt : %d", cnt));
        }

        return item;
    }
}
