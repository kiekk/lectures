package io.springbatch.studyspringbatch;

import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Integer> {

    @Override
    public void beforeRead() {
        System.out.println("Before Read");
    }

    @Override
    public void afterRead(Integer item) {
        System.out.println("After Read");

    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("After Read Error");

    }
}
