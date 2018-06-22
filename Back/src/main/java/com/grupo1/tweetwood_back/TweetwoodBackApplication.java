package com.grupo1.tweetwood_back;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@JsonAutoDetect
@EnableAutoConfiguration

public class TweetwoodBackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TweetwoodBackApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TweetwoodBackApplication.class);
    }
}

