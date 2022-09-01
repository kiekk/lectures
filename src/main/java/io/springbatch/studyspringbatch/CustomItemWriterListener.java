package io.springbatch.studyspringbatch;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class CustomItemWriterListener implements ItemWriteListener<String> {

    @Override
    public void beforeWrite(List<? extends String> items) {
        System.out.println("Before Write");
    }

    @Override
    public void afterWrite(List<? extends String> items) {
        System.out.println("After Write");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends String> items) {
        System.out.println("After Write Error");

    }
}
