package com.tobyspring.studytobyspring.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public int calcSum(String filePath) throws IOException {
        LineCallback<Integer> callback = (line, value) -> value + Integer.parseInt(line);

        return lineReadTemplate(filePath, callback, 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        LineCallback<Integer> callback = (line, value) -> value * Integer.parseInt(line);

        return lineReadTemplate(filePath, callback, 1);
    }

    public String concatenate(String filePath) throws IOException {
        LineCallback<String> callback = (line, value) -> value + line;
        return lineReadTemplate(filePath, callback, "");
    }

    public Integer fileReaderTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            T res = initVal;
            String line;

            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
