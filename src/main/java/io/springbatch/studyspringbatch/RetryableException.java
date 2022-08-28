package io.springbatch.studyspringbatch;

public class RetryableException extends RuntimeException {

    public RetryableException() {
        super();
    }

    public RetryableException(String message) {
        super(message);
    }
}
