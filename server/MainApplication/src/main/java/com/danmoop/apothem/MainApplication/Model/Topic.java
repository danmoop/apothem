package com.danmoop.apothem.MainApplication.Model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Topic
{
    @Id
    private String id;

    private String author;
    private String name;

    private List<String> subscribers;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }
}