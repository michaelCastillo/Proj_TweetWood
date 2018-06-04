package com.grupo1.tweetwood_back.Mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetService {
	@Autowired
	TweetRepository repository;
	
	@RequestMapping("/tweets")
	public List<Tweet> getTweets(){
		return repository.findAll();		
	}
}
