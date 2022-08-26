package io.springbatch.studyspringbatch;

import org.springframework.batch.item.ItemProcessor;

public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.printf("ItemProcessor : %s%n", item);
        if (item.equals("6") || item.equals("7")) {
            throw new SkippableException(String.format("Process Failed cnt : %d", cnt));
        } else {
            return String.valueOf(Integer.parseInt(item) * -1);
        }
    }
}
