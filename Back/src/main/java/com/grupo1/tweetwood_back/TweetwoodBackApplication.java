package com.grupo1.tweetwood_back;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@JsonAutoDetect
@EnableAutoConfiguration
public class TweetwoodBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetwoodBackApplication.class, args);
    }
}
