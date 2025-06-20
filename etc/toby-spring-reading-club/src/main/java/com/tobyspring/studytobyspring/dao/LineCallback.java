package com.tobyspring.studytobyspring.dao;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
