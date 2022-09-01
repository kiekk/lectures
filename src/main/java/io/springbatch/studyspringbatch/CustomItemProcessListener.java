package io.springbatch.studyspringbatch;

import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Integer, String> {

    @Override
    public void beforeProcess(Integer item) {
        System.out.println("Before Process");
    }

    @Override
    public void afterProcess(Integer item, String result) {
        System.out.println("After Process");

    }

    @Override
    public void onProcessError(Integer item, Exception e) {
        System.out.println("After Process Error");

    }
}
