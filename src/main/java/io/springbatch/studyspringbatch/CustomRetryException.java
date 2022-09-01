package io.springbatch.studyspringbatch;

public class CustomRetryException extends Exception {
    public CustomRetryException(String message) {
        super(message);
    }
}
