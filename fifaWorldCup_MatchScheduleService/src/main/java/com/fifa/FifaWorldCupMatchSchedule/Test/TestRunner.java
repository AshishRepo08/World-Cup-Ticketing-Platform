package com.fifa.FifaWorldCupMatchSchedule.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Mongo URL = " + uri);
    }
}
