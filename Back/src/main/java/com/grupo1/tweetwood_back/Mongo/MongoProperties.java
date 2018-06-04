package com.grupo1.tweetwood_back.Mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mongo")
public class MongoProperties {
	private	String host;
	private	String database;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = "localhost";
		System.out.println(this.host);
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = "twitter";
        System.out.println(this.database);
    }
}
