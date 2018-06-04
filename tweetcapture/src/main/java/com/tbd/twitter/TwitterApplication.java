package com.tbd.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TwitterApplication {

	public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class,args);
	}
}
