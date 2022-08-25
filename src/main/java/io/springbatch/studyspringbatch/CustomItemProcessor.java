package io.springbatch.studyspringbatch;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<ProcessorInfo, ProcessorInfo> {

    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("CustomItemProcessor1");
        return item;
    }
}
