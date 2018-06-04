package com.grupo1.tweetwood_back.Mongo;


import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface TweetRepository extends MongoRepository<Tweet,Long>{

}
