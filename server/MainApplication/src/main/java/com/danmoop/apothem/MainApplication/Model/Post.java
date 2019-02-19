package com.danmoop.apothem.MainApplication.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "posts")
public class Post
{
    private String author;
    private String topic;
    private String title;
    private String content;
    private String createdOn;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void generateTime()
    {
        this.createdOn = new Date().toLocaleString();
    }
}