package com.tobyspring.studytobyspring.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.*;

public class Calculator {

    public int calcSum(String filePath) throws IOException {
        BufferedReaderCallback callback = br -> {
            int sum = 0;
            String line;

            while ((line = br.readLine()) != null) {
                sum += parseInt(line);
            }

            return sum;
        };
        return fileReaderTemplate(filePath, callback);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        BufferedReaderCallback callback = br -> {
            int multiply = 1;
            String line;

            while ((line = br.readLine()) != null) {
                multiply *= parseInt(line);
            }

            return multiply;
        };
        return fileReaderTemplate(filePath, callback);
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
}
