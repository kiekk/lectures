package io.springbatch.studyspringbatch;

public class CustomService<T> {

    public void customWrite(T item) {
        System.out.println(item);
    }
}
