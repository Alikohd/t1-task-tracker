package com.example.t1tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class T1TaskTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1TaskTrackerApplication.class, args);
    }

}
