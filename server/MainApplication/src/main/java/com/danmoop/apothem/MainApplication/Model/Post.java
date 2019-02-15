package com.danmoop.apothem.MainApplication.Model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "posts")
public class Post
{
    private String author;
    private String topic;
    private String title;

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
}