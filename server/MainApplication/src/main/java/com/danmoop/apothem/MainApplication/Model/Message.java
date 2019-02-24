package com.danmoop.apothem.MainApplication.Model;

import java.time.LocalDateTime;

public class Message {

    private String author;
    private String message;
    private String sentOn;

    public void generateTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        this.sentOn = localDateTime.getDayOfMonth()
                + "." + localDateTime.getMonthValue()
                + "." + localDateTime.getYear()
                + " " + localDateTime.getHour()
                + ":" + localDateTime.getMinute()
                + ":" + localDateTime.getSecond();
    }

    public String getAuthor()
    {
        return author;
    }

    public String getMessage()
    {
        return message;
    }
}