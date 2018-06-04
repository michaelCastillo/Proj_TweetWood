package com.grupo1.tweetwood_back;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import com.grupo1.tweetwood_back.lucene.lucene;
@SpringBootApplication
@JsonAutoDetect
@EnableAutoConfiguration
@ComponentScan({"com.grupo1.tweetwood_back","com.grupo1.tweetwood_back.Mongo"})

public class TweetwoodBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetwoodBackApplication.class, args);
        //lucene l = new lucene();
        //l.exec_lucene();
    }
}
