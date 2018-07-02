package com.grupo1.tweetwood_back.modules;

import java.util.Collections;
import java.util.Comparator;

public class Tweet {

    private String id;
    private String text;
    private Double value;

    public Tweet(){

    }

    public Tweet(String id, String text, Double value) {
        this.id = id;
        this.text = text;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}

