package com.soono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class HttpServletResponseProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpServletResponseProcessApplication.class, args);
    }

}
