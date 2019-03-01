package com.danmoop.apothem.MainApplication.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(value = "posts")
public class Post
{
    @Id
    private String id;

    private String author;
    private String topic;
    private String title;
    private String content;
    private String createdOn;
    private String key;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void generateKey() {
        this.key = UUID.randomUUID().toString();
    }

    public String getKey() {
        return key;
    }

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

    public void generateData()
    {
        generateKey();
        generateTime();
        setComments(new ArrayList<>());
    }

    public void generateTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        this.createdOn = localDateTime.getDayOfMonth()
                + "." + localDateTime.getMonthValue()
                + "." + localDateTime.getYear()
                + " " + localDateTime.getHour()
                + ":" + localDateTime.getMinute()
                + ":" + localDateTime.getSecond();
    }

    public void addComment(Comment comment)
    {
        comments.add(comment);
    }
}